INSERT  INTO
	jimu_report_link
      ( 
      ID                            
      ,REPORT_ID                      
      ,PARAMETER                      
      ,EJECT_TYPE                     
      ,LINK_NAME                      
      ,API_METHOD                     
      ,LINK_TYPE                      
      ,API_URL
      ,LINK_CHART_ID
      ,requirement
      ) 
values
      (
      :jimuReportLink.id                            
      ,:jimuReportLink.reportId                      
      ,:jimuReportLink.parameter                     
      ,:jimuReportLink.ejectType                     
      ,:jimuReportLink.linkName                      
      ,:jimuReportLink.apiMethod                     
      ,:jimuReportLink.linkType                      
      ,:jimuReportLink.apiUrl
      ,:jimuReportLink.linkChartId
      ,:jimuReportLink.requirement
      )