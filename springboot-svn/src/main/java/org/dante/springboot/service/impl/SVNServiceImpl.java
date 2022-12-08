package org.dante.springboot.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.dante.springboot.service.ISVNService;
import org.dante.springboot.vo.SvnQueryVO;
import org.dante.springboot.vo.SvnRecordVO;
import org.springframework.stereotype.Service;
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

import com.google.common.collect.Lists;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SVNServiceImpl implements ISVNService {
	
	private ISVNAuthenticationManager authManager;
	private DefaultSVNOptions options;
	
	@PostConstruct
	public void init() {
		options = SVNWCUtil.createDefaultOptions(true);
		options.setDiffCommand("-x -w");
		authManager= SVNWCUtil.createDefaultAuthenticationManager( "sunchao", "sunchao4321".toCharArray());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<SvnRecordVO> getRecord(SvnQueryVO query) throws Exception {
		Date startDate = DateUtil.beginOfDay(query.getStartDate());
		Date endDate = query.getEndDate();
		if(ObjectUtil.isNull(endDate)) {
			endDate = DateUtil.endOfDay(startDate);
		} else {
			endDate = DateUtil.endOfDay(endDate);
		}
		List<SvnRecordVO> records = Lists.newArrayList();
		try {
			SVNRepository svnRepository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(query.getSvnUrl()));
			svnRepository.setAuthenticationManager(authManager);
			long startRevision = svnRepository.getDatedRevision(startDate);
	        long endRevision = svnRepository.getDatedRevision(endDate);
			Collection<SVNLogEntry> logEntries = svnRepository.log(new String[]{""}, null,
	                startRevision, endRevision, true, true);
			SVNLogEntry[] svnLogEntries = logEntries.toArray(new SVNLogEntry[0]);
			for (SVNLogEntry svnLogEntry : svnLogEntries) {
	        	SvnRecordVO record = new SvnRecordVO();
	        	record.setAccount(svnLogEntry.getAuthor());
	        	record.setCommitDate(svnLogEntry.getDate());
	        	record.setCommitMessage(svnLogEntry.getMessage());
	        	record.setRevision(svnLogEntry.getRevision());
	        	records.add(record);
	        }
			groupStat(query.getSvnUrl(), records);
		} catch (SVNException e) {
			log.info(e.getMessage(), e);
			throw new Exception(e);
		}
		return records;
	}
	
	private List<List<SvnRecordVO>> groupRecord(List<SvnRecordVO> list, int thread) {
    	List<List<SvnRecordVO>> groupRecords = Lists.newArrayList();
		int size = list.size();
		if(thread >= size) {
			for (int i = 0; i < size; i++) {
				groupRecords.add(list.subList(i, i + 1));
			}
		} else {
			for (int i = 0; i < thread; i++) {
				int to = size / thread * (i + 1);
				if(i == thread - 1) {
					to += size % thread;
				}
				groupRecords.add(list.subList(size / thread * i, to));
			}
		}
		return groupRecords;
    }
	
	private void groupStat(String svnUrl, List<SvnRecordVO> records) {
		int nThreads = Runtime.getRuntime().availableProcessors();
		ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
		List<CompletableFuture<Integer>> futures = Lists.newArrayList();
		List<List<SvnRecordVO>> groupRecords = groupRecord(records, nThreads);
		groupRecords.forEach(groupList -> {
			futures.add(CompletableFuture.supplyAsync(() -> groupList).thenApplyAsync(s -> fillSvnRecordCodeline(svnUrl, s), executorService));
		});
		CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
		allOf.join();
	}
	
	private int fillSvnRecordCodeline(String svnUrl, List<SvnRecordVO> records) {
		int result = 0;
		for (SvnRecordVO record : records) {
			record.setTotalCodeLine(getAddLinesByVersion(record.getRevision(), svnUrl));
			result++;
		}
		return result;
	}
	
	private int getAddLinesByVersion(long version, String svnUrl) {
        String logContent = null;
        try {
            SVNDiffClient diffClient = new SVNDiffClient(authManager, options);
            diffClient.setGitDiffFormat(true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            diffClient.doDiff(SVNURL.parseURIEncoded(svnUrl),
                    SVNRevision.create(version - 1),
                    SVNURL.parseURIEncoded(svnUrl),
                    SVNRevision.create(version),
                    SVNDepth.UNKNOWN, true, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            logContent = new String(bytes);
            byteArrayOutputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        int codeLine = getTotalAddLinesFromLogContent(logContent);
        return codeLine;
    }
 
    public int getTotalAddLinesFromLogContent(String logContent) {
        int totalAddLine=0;
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
