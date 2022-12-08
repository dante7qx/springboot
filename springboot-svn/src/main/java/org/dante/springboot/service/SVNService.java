package org.dante.springboot.service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import cn.hutool.core.date.DateUtil;

/**
 * JAVA代码实现svn的代码量统计
 * 
 * https://blog.csdn.net/weixin_41793807/article/details/82699305
 * https://blog.csdn.net/qq_35788725/article/details/88192205
 * https://blog.csdn.net/u012621115/article/details/51851572
 * 
 * @author dante
 *
 */
public class SVNService {
	
	
	public static void main(String[] args) throws Exception {
		SVNService svn = new SVNService("sunchao", "sunchao4321", "svn://116.176.33.76:8443/java/tsccs_ibs");
        System.out.println(System.currentTimeMillis());
        int count = svn.getAddLinesByDateRangeAndAuthorName(DateUtil.parseDate("2022-11-27"), DateUtil.parseDate("2022-11-28"), "zhangjing");
        System.out.println("总行数：" + count);
        System.out.println(System.currentTimeMillis());

	}
    
    String username;
    String password;
    String url;
    DefaultSVNOptions options ;
    ISVNAuthenticationManager authManager;
    SVNRepository svnRepository;
    
    public SVNService(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
        init();
    }
 
    void init() {
        options= SVNWCUtil.createDefaultOptions(true);
        options.setDiffCommand("-x -w");
        authManager= SVNWCUtil.createDefaultAuthenticationManager( username, password.toCharArray());;
        try {
            svnRepository= SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
        } catch (SVNException e) {
            e.printStackTrace();
        }
        svnRepository.setAuthenticationManager(authManager);
    }
 
    public int getAddLinesByDateRangeAndAuthorName(Date startDate, Date endDate,String authorName) throws Exception {
        int count = 0;
        List<SVNLogEntry> SVNLogEntryList = getLogFromSvnRepository(startDate, endDate, authorName);
        for (SVNLogEntry svnLogEntry : SVNLogEntryList) {
            long revision = svnLogEntry.getRevision();
            count += getAddLinesByVersion(revision);
        }
        return count;
    }
 
    public  List<SVNLogEntry> getLogFromSvnRepository(Date startDate, Date endDate,String authorName) throws Exception {
        long startRevision = svnRepository.getDatedRevision(startDate);
        long endRevision = svnRepository.getDatedRevision(endDate);
        @SuppressWarnings("unchecked")
        Collection<SVNLogEntry> logEntries = svnRepository.log(new String[]{""}, null,
                startRevision, endRevision, true, true);
        SVNLogEntry[] svnLogEntries = logEntries.toArray(new SVNLogEntry[0]);
        List<SVNLogEntry> list = new ArrayList<>();
        for (SVNLogEntry svnLogEntry : svnLogEntries) {
            String author = svnLogEntry.getAuthor();
            if (author!=null&&author.equals(authorName)) {
            	System.out.println(DateUtil.formatDateTime(svnLogEntry.getDate()) + " ==> " + svnLogEntry.getMessage() + " ==> " + svnLogEntry.getRevision());
                list.add(svnLogEntry);
            }
        }
        return list;
    }
 
 
    public  int getAddLinesByVersion(long version) {
        long l2 = System.currentTimeMillis();
        String logContent=null;
        try {
            SVNDiffClient diffClient = new SVNDiffClient(authManager, options);
            diffClient.setGitDiffFormat(true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            diffClient.doDiff(SVNURL.parseURIEncoded(url),
                    SVNRevision.create(version-1),
                    SVNURL.parseURIEncoded(url),
                    SVNRevision.create(version),
                    SVNDepth.UNKNOWN, true, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            logContent= new String(bytes);
            byteArrayOutputStream.close();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        long l3 = System.currentTimeMillis();
        int codeLine = getTotalAddLinesFromLogContent(logContent);
        System.out.println("通过版本号[ " + version + " ]获取代码增加行数时间："+(l3 - l2) + "，行数：" + codeLine);
        return codeLine;
    }
 
    public int getTotalAddLinesFromLogContent(String logContent) {
        int totalAddLine=0;
//        System.out.println(logContent);
        for (String s : logContent.split("\n")) {
            for (char c : s.toCharArray()) {
                if(c=='+'){
                    totalAddLine++;
                }else if (c == '-') {
                    totalAddLine--;
                }else {
                    break;
                }
            }
        }
        return totalAddLine;
    }
	
}
