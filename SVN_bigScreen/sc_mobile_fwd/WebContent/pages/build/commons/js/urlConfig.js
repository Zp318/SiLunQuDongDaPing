 /**
  * [URL配置说明]
  * @registerSuccessRate: VoLTE注册成功率
  * @connectRate: VoLTE接通率
  * @connectDelay: VoLTE接通时延
  * @srvccSuccessRate: SRVCC切换成功率
  * @callDropRate: VoLTE掉话率
  * @mosGoodRate: MOS(大于3.0占比)
  * @fallbackSuccessRate: CSFB回落成功率
  * @callTimePercent: Volte通话时长占比
  * @openSucceccRate: 业务自动开通成功率
  * @terminalPermeability: Volte终端渗透率
  */
window.urlConfig = {
	registerSuccessRate: "https://10.25.122.70:8443/SF_SC_VOLTE/volteKpiAnalysis/initPage.action",
  connectRate: "https://10.25.122.70:8443/SF_SC_VOLTE/volteKpiAnalysis/initPage.action",
  connectDelay: "https://10.25.122.70:8443/sdpi/pages/dashboard/dashboard.jsp",
  srvccSuccessRate: "https://10.25.122.70:8443/SF_SC_VOLTE/volteKpiAnalysis/initPage.action",
  callDropRate: "https://10.25.122.70:8443/SF_SC_VOLTE/volteKpiAnalysis/initPage.action",
  mosGoodRate: "https://10.25.122.70:8443/SF_SC_VOLTE/volteKpiAnalysis/initPage.action",
  fallbackSuccessRate: "https://10.25.122.70:8443/CSFB_ANALYSIS/pages/csfb/performance_analysis.jsp",
  callTimePercent: "https://10.25.122.70:8443/SF_VOLTE_CALLDURATION/rankingsAnalysisPages/initPage.action",
  openSucceccRate: "https://10.25.122.70:8443/CUSTER_AHCMCC_VOLTE_WEB/pages/index.jsp",
  terminalPermeability: "https://10.25.122.70:8443/SF_SC_VOLTE/volteTerminalAnalysis/initPage.action"
}