CREATE OR REPLACE PROCEDURE PROC_PENDSDATA_CHECK(p_REPORTINST IN varchar2,p_ENDMONTH IN varchar2,FIRSTRESULT IN VARCHAR2,PAGECOUNT IN VARCHAR2,rt OUT sys_refcursor)
AS

BEGIN

OPEN rt FOR
SELECT JRJGDM,YWH,KHRQ,YWZLXF,YE,XM,ZJLX,ZJHM FROM GR_GRXX_JC WHERE EXISTS (
SELECT * FROM (SELECT YWH,MAX(JSYHKRQ) AS JSYHKRQ FROM GR_GRXX_JC,REPORTINSTSUBINFO
WHERE INSTINFO = STRINSTCODE AND strReportInstCode = p_REPORTINST AND SUBSTR(JSYHKRQ,1,6) <= p_ENDMONTH AND RPTFeedbackType = '2' GROUP BY YWH) TEMP
WHERE GR_GRXX_JC.YWH = TEMP.YWH AND GR_GRXX_JC.JSYHKRQ = TEMP.JSYHKRQ AND GR_GRXX_JC.YE <> 0);

END PROC_PEndsData_Check;