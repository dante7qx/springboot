-- 下载数据： https://clickhouse-public-datasets.s3.amazonaws.com/github_events_v2.native.xz
-- 导入数据： pixz -d < github_events.native.xz | clickhouse-client --port 9000 --query "INSERT INTO github_events FORMAT Native"

-- 创建表
CREATE TABLE github_events
(
    file_time DateTime,
    event_type Enum('CommitCommentEvent' = 1, 'CreateEvent' = 2, 'DeleteEvent' = 3, 'ForkEvent' = 4,
                    'GollumEvent' = 5, 'IssueCommentEvent' = 6, 'IssuesEvent' = 7, 'MemberEvent' = 8,
                    'PublicEvent' = 9, 'PullRequestEvent' = 10, 'PullRequestReviewCommentEvent' = 11,
                    'PushEvent' = 12, 'ReleaseEvent' = 13, 'SponsorshipEvent' = 14, 'WatchEvent' = 15,
                    'GistEvent' = 16, 'FollowEvent' = 17, 'DownloadEvent' = 18, 'PullRequestReviewEvent' = 19,
                    'ForkApplyEvent' = 20, 'Event' = 21, 'TeamAddEvent' = 22),
    actor_login LowCardinality(String),
    repo_name LowCardinality(String),
    created_at DateTime,
    updated_at DateTime,
    action Enum('none' = 0, 'created' = 1, 'added' = 2, 'edited' = 3, 'deleted' = 4, 'opened' = 5, 'closed' = 6, 'reopened' = 7, 'assigned' = 8, 'unassigned' = 9,
                'labeled' = 10, 'unlabeled' = 11, 'review_requested' = 12, 'review_request_removed' = 13, 'synchronize' = 14, 'started' = 15, 'published' = 16, 'update' = 17, 'create' = 18, 'fork' = 19, 'merged' = 20),
    comment_id UInt64,
    body String,
    path String,
    position Int32,
    line Int32,
    ref LowCardinality(String),
    ref_type Enum('none' = 0, 'branch' = 1, 'tag' = 2, 'repository' = 3, 'unknown' = 4),
    creator_user_login LowCardinality(String),
    number UInt32,
    title String,
    labels Array(LowCardinality(String)),
    state Enum('none' = 0, 'open' = 1, 'closed' = 2),
    locked UInt8,
    assignee LowCardinality(String),
    assignees Array(LowCardinality(String)),
    comments UInt32,
    author_association Enum('NONE' = 0, 'CONTRIBUTOR' = 1, 'OWNER' = 2, 'COLLABORATOR' = 3, 'MEMBER' = 4, 'MANNEQUIN' = 5),
    closed_at DateTime,
    merged_at DateTime,
    merge_commit_sha String,
    requested_reviewers Array(LowCardinality(String)),
    requested_teams Array(LowCardinality(String)),
    head_ref LowCardinality(String),
    head_sha String,
    base_ref LowCardinality(String),
    base_sha String,
    merged UInt8,
    mergeable UInt8,
    rebaseable UInt8,
    mergeable_state Enum('unknown' = 0, 'dirty' = 1, 'clean' = 2, 'unstable' = 3, 'draft' = 4),
    merged_by LowCardinality(String),
    review_comments UInt32,
    maintainer_can_modify UInt8,
    commits UInt32,
    additions UInt32,
    deletions UInt32,
    changed_files UInt32,
    diff_hunk String,
    original_position UInt32,
    commit_id String,
    original_commit_id String,
    push_size UInt32,
    push_distinct_size UInt32,
    member_login LowCardinality(String),
    release_tag_name String,
    release_name String,
    review_state Enum('none' = 0, 'approved' = 1, 'changes_requested' = 2, 'commented' = 3, 'dismissed' = 4, 'pending' = 5)
)
ENGINE = MergeTree
ORDER BY (event_type, repo_name, created_at);

-- 使用参考 https://ghe.clickhouse.tech/#download-the-dataset

-- 获得 star 的 Github Repo
SELECT count() FROM github_events WHERE event_type = 'WatchEvent';

-- 取消 start 的 Github Repo
SELECT action, count() FROM github_events WHERE event_type = 'WatchEvent' GROUP BY action;

-- ClickHouse 的 star 数
SELECT count() FROM github_events WHERE event_type = 'WatchEvent' AND repo_name IN ('ClickHouse/ClickHouse', 'yandex/ClickHouse') GROUP BY action;

-- Top 50 star Github Repo
SELECT repo_name, count() AS stars FROM github_events WHERE event_type = 'WatchEvent' GROUP BY repo_name ORDER BY stars DESC LIMIT 50;
SELECT repo_name, count() AS stars FROM github_events WHERE event_type = 'WatchEvent' AND toYear(created_at) = '2020' GROUP BY repo_name ORDER BY stars DESC LIMIT 50;


-- Github Reop 按 star 统计的分布情况
SELECT
    exp10(floor(log10(c))) AS stars,
    uniq(k)
FROM
(
    SELECT
        repo_name AS k,
        count() AS c
    FROM github_events
    WHERE event_type = 'WatchEvent'
    GROUP BY k
)
GROUP BY stars
ORDER BY stars ASC;

-- Github 中 repo 的总数
SELECT uniq(repo_name) FROM github_events;

-- 年度报告
SELECT
    year,
    lower(repo_name) AS repo,
    count()
FROM github_events
WHERE (event_type = 'WatchEvent') AND (year >= 2015)
GROUP BY
    repo,
    toYear(created_at) AS year
ORDER BY
    year ASC,
    count() DESC
LIMIT 10 BY YEAR;

-- 多线图
SELECT
    repo AS name,
    groupArrayInsertAt(toUInt32(c), toUInt64(dateDiff('month', toDate('2015-01-01'), month))) AS data
FROM
(
    SELECT
        lower(repo_name) AS repo,
        toStartOfMonth(created_at) AS month,
        count() AS c
    FROM github_events
    WHERE (event_type = 'WatchEvent') AND (toYear(created_at) >= 2015) AND (repo IN
    (
        SELECT lower(repo_name) AS repo
        FROM github_events
        WHERE (event_type = 'WatchEvent') AND (toYear(created_at) >= 2015)
        GROUP BY repo
        ORDER BY count() DESC
        LIMIT 10
    ))
    GROUP BY
        repo,
        month
)
GROUP BY repo
ORDER BY repo ASC;

-- 按年统计 star repo 的变化趋势
 SELECT toYear(created_at) AS year, count() AS stars, bar(stars, 0, 50000000, 10) AS bar FROM github_events WHERE event_type = 'WatchEvent' GROUP BY year ORDER BY YEAR;
 
-- star（收藏） repo人的Top 50
SELECT actor_login, count() AS stars FROM github_events WHERE event_type = 'WatchEvent' GROUP BY actor_login ORDER BY stars DESC LIMIT 50;
-- 我的收藏
SELECT actor_login, count() AS stars FROM github_events WHERE event_type = 'WatchEvent' AND actor_login = 'dante7qx' GROUP BY actor_login ORDER BY stars DESC LIMIT 50;

-- 收藏排名Repo，Top 50
SELECT
    repo_name,
    count() AS stars
FROM github_events
WHERE (event_type = 'WatchEvent') AND (repo_name IN
(
    SELECT repo_name
    FROM github_events
    WHERE (event_type = 'WatchEvent') AND (actor_login = 'alexey-milovidov')
))
GROUP BY repo_name
ORDER BY stars DESC
LIMIT 50;

-- 收藏 vuejs/vue 的人，还收藏的repo排名，Top 50
SELECT
    repo_name,
    count() AS stars
FROM github_events
WHERE (event_type = 'WatchEvent') AND (actor_login IN
(
    SELECT actor_login
    FROM github_events
    WHERE (event_type = 'WatchEvent') AND (repo_name = 'vuejs/vue')
)) AND (repo_name != 'vuejs/vue')
GROUP BY repo_name
ORDER BY stars DESC
LIMIT 50;

-- 通过在 star repo 上的交叉点找到一个朋友
WITH repo_name IN
    (
        SELECT repo_name
        FROM github_events
        WHERE (event_type = 'WatchEvent') AND (actor_login IN ('dante7qx'))
    ) AS is_my_repo
SELECT
    actor_login,
    sum(is_my_repo) AS stars_my,
    sum(NOT is_my_repo) AS stars_other,
    round(stars_my / (203 + stars_other), 3) AS ratio
FROM github_events
WHERE event_type = 'WatchEvent'
GROUP BY actor_login
ORDER BY ratio DESC
LIMIT 50;

-- 对ClickHouse有贡献的作者也对哪些资源库有贡献
SELECT
    repo_name,
    count() AS prs,
    uniq(actor_login) AS authors
FROM github_events
WHERE (event_type = 'PullRequestEvent') AND (action = 'opened') AND (actor_login IN
(
    SELECT actor_login
    FROM github_events
    WHERE (event_type = 'PullRequestEvent') AND (action = 'opened') AND (repo_name IN ('yandex/ClickHouse', 'ClickHouse/ClickHouse'))
)) AND (repo_name NOT ILIKE '%ClickHouse%')
GROUP BY repo_name
ORDER BY authors DESC
LIMIT 50；

-- 在ClickHouse提交 issue 的作者也在哪些资料库中提交问题？
SELECT
    repo_name,
    count() AS prs,
    uniq(actor_login) AS authors
FROM github_events
WHERE (event_type = 'IssuesEvent') AND (action = 'opened') AND (actor_login IN
(
    SELECT actor_login
    FROM github_events
    WHERE (event_type = 'IssuesEvent') AND (action = 'opened') AND (repo_name IN ('yandex/ClickHouse', 'ClickHouse/ClickHouse'))
)) AND (repo_name NOT ILIKE '%ClickHouse%')
GROUP BY repo_name
ORDER BY authors DESC
LIMIT 50;

-- 每天被收藏的repo，Top 50
SELECT
    repo_name,
    toDate(created_at) AS day,
    count() AS stars
FROM github_events
WHERE event_type = 'WatchEvent'
GROUP BY
    repo_name,
    day
ORDER BY count() DESC
LIMIT 1 BY repo_name
LIMIT 50;

-- 同比增长最高的 repo，Top 50
WITH toYear(created_at) AS year
SELECT
    repo_name,
    sum(year = 2020) AS stars2020,
    sum(year = 2019) AS stars2019,
    round(stars2020 / stars2019, 3) AS yoy,
    min(created_at) AS first_seen
FROM github_events
WHERE event_type = 'WatchEvent'
GROUP BY repo_name
HAVING (min(created_at) <= '2019-01-01 00:00:00') AND (stars2019 >= 1000)
ORDER BY yoy DESC
LIMIT 50;

-- 停滞情况最严重的 repo，Top 50
WITH toYear(created_at) AS year
SELECT
    repo_name,
    sum(year = 2020) AS stars2020,
    sum(year = 2019) AS stars2019,
    round(stars2020 / stars2019, 3) AS yoy,
    min(created_at) AS first_seen
FROM github_events
WHERE event_type = 'WatchEvent'
GROUP BY repo_name
HAVING (min(created_at) <= '2019-01-01 00:00:00') AND (max(created_at) >= '2020-06-01 00:00:00') AND (stars2019 >= 1000)
ORDER BY yoy ASC
LIMIT 50;

-- 在一段时间内增长最稳定的 repo，Top 50
SELECT
    repo_name,
    max(stars) AS daily_stars,
    sum(stars) AS total_stars,
    total_stars / daily_stars AS rate
FROM
(
    SELECT
        repo_name,
        toDate(created_at) AS day,
        count() AS stars
    FROM github_events
    WHERE event_type = 'WatchEvent'
    GROUP BY
        repo_name,
        day
)
GROUP BY repo_name
ORDER BY rate DESC
LIMIT 50;

-- 一周中每天的 star 统计
SELECT toDayOfWeek(created_at) AS day, count() AS stars, bar(stars, 0, 50000000, 10) AS bar FROM github_events WHERE event_type = 'WatchEvent' GROUP BY day ORDER BY DAY;

-- Github 上的所有用户、收藏过repo、至少推送过一次、提交过PR的用户
SELECT uniq(actor_login) FROM github_events;
SELECT uniq(actor_login) FROM github_events WHERE event_type = 'WatchEvent';
SELECT uniq(actor_login) FROM github_events WHERE event_type = 'PushEvent';

-- 至少在一个 repo 中，至少提交过一次 PR 的顶级 repo，Top 50
SELECT
    repo_name,
    count()
FROM github_events
WHERE (event_type = 'WatchEvent') AND (actor_login IN
(
    SELECT actor_login
    FROM github_events
    WHERE (event_type = 'PullRequestEvent') AND (action = 'opened')
))
GROUP BY repo_name
ORDER BY count() DESC
LIMIT 50;

-- 10次 PR 的顶级 repo，Top 50
SELECT
    repo_name,
    count()
FROM github_events
WHERE (event_type = 'WatchEvent') AND (actor_login IN
(
    SELECT actor_login
    FROM github_events
    WHERE (event_type = 'PullRequestEvent') AND (action = 'opened')
    GROUP BY actor_login
    HAVING count() >= 10
))
GROUP BY repo_name
ORDER BY count() DESC
LIMIT 50;

-- 拥有最大数量 PR 的 repo
SELECT repo_name, count(), uniq(actor_login) FROM github_events WHERE event_type = 'PullRequestEvent' AND action = 'opened' GROUP BY repo_name ORDER BY count() DESC LIMIT 50;

-- 最大 pull request 的 repo, Top 50
SELECT repo_name, count(), uniq(actor_login) AS u FROM github_events WHERE event_type = 'PullRequestEvent' AND action = 'opened' GROUP BY repo_name ORDER BY u DESC LIMIT 50;

-- Issue 数量最多的储存库, Top 50
SELECT repo_name, count() AS c, uniq(actor_login) AS u FROM github_events WHERE event_type = 'IssuesEvent' AND action = 'opened' GROUP BY repo_name ORDER BY c DESC LIMIT 50;

-- 按收藏排名，Issue 数量最多的储存库, Top 50
WITH (event_type = 'IssuesEvent') AND (action = 'opened') AS issue_created
SELECT
    repo_name,
    sum(issue_created) AS c,
    uniqIf(actor_login, issue_created) AS u,
    sum(event_type = 'WatchEvent') AS stars
FROM github_events
WHERE event_type IN ('IssuesEvent', 'WatchEvent')
GROUP BY repo_name
ORDER BY c DESC
LIMIT 50;

-- 区分真正的问题和机器人的问题，让我们在1000颗星上加上一个分界线
WITH (event_type = 'IssuesEvent') AND (action = 'opened') AS issue_created
SELECT
    repo_name,
    sum(issue_created) AS c,
    uniqIf(actor_login, issue_created) AS u,
    sum(event_type = 'WatchEvent') AS stars
FROM github_events
WHERE event_type IN ('IssuesEvent', 'WatchEvent')
GROUP BY repo_name
HAVING stars >= 1000
ORDER BY c DESC
LIMIT 50;

-- 按照问题作者的数量进行排序
WITH (event_type = 'IssuesEvent') AND (action = 'opened') AS issue_created
SELECT
    repo_name,
    sum(issue_created) AS c,
    uniqIf(actor_login, issue_created) AS u,
    sum(event_type = 'WatchEvent') AS stars
FROM github_events
WHERE event_type IN ('IssuesEvent', 'WatchEvent')
GROUP BY repo_name
ORDER BY u DESC
LIMIT 50;

-- 拥有最多 push 权限的 repo，Top 50
SELECT repo_name, uniqIf(actor_login, event_type = 'PushEvent') AS u, sum(event_type = 'WatchEvent') AS stars FROM github_events WHERE event_type IN ('PushEvent', 'WatchEvent') AND repo_name != '/' GROUP BY repo_name ORDER BY u DESC LIMIT 50;

-- 拥有主分支推送权限的人数最多的 repo，Top 50
SELECT
    repo_name,
    uniqIf(actor_login, (event_type = 'PushEvent') AND match(ref, '/(main|master)$')) AS u,
    sum(event_type = 'WatchEvent') AS stars
FROM github_events
WHERE (event_type IN ('PushEvent', 'WatchEvent')) AND (repo_name != '/')
GROUP BY repo_name
ORDER BY u DESC
LIMIT 50;

-- star >= 100，拥有主分支推送权限的人数最多的 repo，Top 50
SELECT
    repo_name,
    uniqIf(actor_login, (event_type = 'PushEvent') AND match(ref, '/(main|master)$')) AS u,
    sum(event_type = 'WatchEvent') AS stars
FROM github_events
WHERE (event_type IN ('PushEvent', 'WatchEvent')) AND (repo_name != '/')
GROUP BY repo_name
HAVING stars >= 100
ORDER BY u DESC
LIMIT 50;

-- fork 排名，Top 50
SELECT repo_name, count() AS forks FROM github_events WHERE event_type = 'ForkEvent' GROUP BY repo_name ORDER BY forks DESC LIMIT 50;

-- stars 和 forks 排名，Top 50
SELECT
    repo_name,
    sum(event_type = 'ForkEvent') AS forks,
    sum(event_type = 'WatchEvent') AS stars,
    round(stars / forks, 3) AS ratio
FROM github_events
WHERE event_type IN ('ForkEvent', 'WatchEvent')
GROUP BY repo_name
ORDER BY forks DESC
LIMIT 50;

-- forks 多，stars 少，Top 50
SELECT
    repo_name,
    sum(event_type = 'ForkEvent') AS forks,
    sum(event_type = 'WatchEvent') AS stars,
    round(forks / stars, 2) AS ratio
FROM github_events
WHERE event_type IN ('ForkEvent', 'WatchEvent')
GROUP BY repo_name
HAVING (stars > 100) AND (forks > 100)
ORDER BY ratio DESC
LIMIT 50;

-- stars 和 forks 之间的总体比例
SELECT sum(event_type = 'ForkEvent') AS forks, sum(event_type = 'WatchEvent') AS stars, round(stars / forks, 2) AS ratio FROM github_events WHERE event_type IN ('ForkEvent', 'WatchEvent');

-- 最受欢迎（forks 和 stars）排名，Top 50
SELECT
    sum(stars) AS stars,
    sum(forks) AS forks,
    round(stars / forks, 2) AS ratio
FROM
(
    SELECT
        sum(event_type = 'ForkEvent') AS forks,
        sum(event_type = 'WatchEvent') AS stars
    FROM github_events
    WHERE event_type IN ('ForkEvent', 'WatchEvent')
    GROUP BY repo_name
    HAVING stars > 100
);

-- issue 有最多回复的 repo 排名，Top 50
SELECT count() FROM github_events WHERE event_type = 'IssueCommentEvent';
SELECT repo_name, count() FROM github_events WHERE event_type = 'IssueCommentEvent' GROUP BY repo_name ORDER BY count() DESC LIMIT 50

-- issue 和 comments 比率
SELECT
    repo_name,
    count() AS comments,
    uniq(number) AS issues,
    round(comments / issues, 2) AS ratio
FROM github_events
WHERE event_type = 'IssueCommentEvent'
GROUP BY repo_name
ORDER BY count() DESC
LIMIT 50;

-- active issues 排名，Top 50
SELECT
    repo_name,
    number,
    count() AS comments
FROM github_events
WHERE (event_type = 'IssueCommentEvent') AND (action = 'created')
GROUP BY
    repo_name,
    number
ORDER BY count() DESC
LIMIT 50;

-- 史诗般的错误（epic bugs），按 issue 编号过滤，Top 50
SELECT
    repo_name,
    number,
    count() AS comments
FROM github_events
WHERE (event_type = 'IssueCommentEvent') AND (action = 'created') AND (number > 10)
GROUP BY
    repo_name,
    number
ORDER BY count() DESC
LIMIT 50;

-- 统计评论作者的数量，并添加一个阈值：
SELECT
    repo_name,
    number,
    count() AS comments,
    uniq(actor_login) AS authors
FROM github_events
WHERE (event_type = 'IssueCommentEvent') AND (action = 'created') AND (number > 10)
GROUP BY
    repo_name,
    number
HAVING authors >= 10
ORDER BY count() DESC
LIMIT 50;

-- 每个顶级存储库的热门评论问题，Top 50
SELECT
    concat('https://github.com/', repo_name, '/issues/', toString(number)) AS URL,
    max(comments),
    argMax(authors, comments) AS authors,
    argMax(number, comments) AS number,
    sum(stars) AS stars
FROM
(
    SELECT *
    FROM
    (
        SELECT
            repo_name,
            number,
            count() AS comments,
            uniq(actor_login) AS authors
        FROM github_events
        WHERE (event_type = 'IssueCommentEvent') AND (action = 'created') AND (number > 10)
        GROUP BY
            repo_name,
            number
        HAVING authors >= 10
    ) AS t1
    INNER JOIN
    (
        SELECT
            repo_name,
            count() AS stars
        FROM github_events
        WHERE event_type = 'WatchEvent'
        GROUP BY repo_name
        HAVING stars > 10000
    ) AS t2 USING (repo_name)
)
GROUP BY repo_name
ORDER BY stars DESC
LIMIT 50;











































































