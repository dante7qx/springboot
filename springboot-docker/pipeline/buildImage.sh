#!/usr/bin/env sh

TAG=${1}
POSTFIX=${2}
case "$1" in
    dev|sit|uat)
        ;;
    *)
        echo "Usage: $0 dev|sit|uat"
        exit -1
esac

BUILDRESULT=`curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d "{
  \"namespace\":\"devopsgroup\",
  \"gitlabBranch\":\"dev\",
  \"gitlabPassword\":\"jenkins0\",
  \"gitlabUser\":\"jenkins\",
  \"imageName\":\"jbh-www-${TAG}${POSTFIX}\",
  \"gitlabRepo\":\"http://gitbj.haihangyun.com/JBH/web.git\",
  \"dockerPath\":\"/container-files/Dockerfile.${TAG}${POSTFIX}\"
}" "https://caas.haihangyun.com/rest/buildImage" | \
  python -c "import sys, json; print json.dumps(json.load(sys.stdin))"`

function getField
{
    Json=${1}
    Field=${2}
    echo $BUILDRESULT | python -c "import sys, json; \
        val = json.load(sys.stdin)[\"${Field}\"]; \
        print unicode(val).encode('utf8')"
}

BUILDSTATUSCODE=`getField "$BUILDRESULT" "statusCode"`
BUILDSTATUSMSG=`getField "$BUILDRESULT" "status"`
if [ "$BUILDSTATUSCODE" = "0" ]; then 
    echo $BUILDRESULT
    IMAGETAG=`echo $BUILDRESULT | python -c 'import sys, json; print json.load(sys.stdin)["imageTag"]'`
    echo $IMAGETAG | cut -d ':' -f 2 > ImageTag${POSTFIX}.tmp
    echo "build image success: $IMAGETAG"
else
    echo "build image fail. Exit code: $BUILDSTATUSCODE, $BUILDSTATUSMSG"
    exit  1
fi

