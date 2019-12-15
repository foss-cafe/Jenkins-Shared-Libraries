import hudson.model.Actionable;
def call(String buildStatus = 'STARTED',String subscribers="admin@example.com"){
// buildStatus of null means successfull
  buildStatus = buildStatus ?: 'SUCCESSFUL'
  subscribers = subscribers ?: 'admin@example.com'
  def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]"
  def branchName = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD')
  def commit = sh(returnStdout: true, script: 'git rev-parse HEAD')
  def author = sh(returnStdout: true, script: "git --no-pager show -s --format='%an'").trim()
  def message = sh(returnStdout: true, script: 'git log -1 --pretty=%B').trim()
  echo "${branchName}"
  echo "${commit}"
  echo "${author}"
  echo "${message}"

  def mail_body_html='''
<!DOCTYPE html>
<head>
  <title>Build report</title>
</head>
<body>

<h1>Build ${buildStatus}</h1>
<table>
  <tr><th>Build URL:</th><td><a href="{RUN_DISPLAY_URL}">${RUN_DISPLAY_URL}</a></td></tr>
  <tr><th>Project:</th><td>${JOB_NAME}</td></tr>
  <tr><th>Build duration:</th><td>${durationString}</td></tr>
  <tr><th>Branch:</th><td>${branch}</td></tr>
  <tr><th>Commit</th><td>${commit}</td></tr>
  <tr><th>Author:</th><td>${author}</td></tr>
  <tr><th>Commit Message:</th><td>${message}</td></tr>
</table>
 
</body>
</html>
''' 
def binding = ["buildStatus":buildStatus, "RUN_DISPLAY_URL":"${env.BUILD_URL}","JOB_NAME":"${env.JOB_NAME}","durationString":"${currentBuild.durationString}","commit":commit,"author":author,"message":message,"branch": branchName]  
def engine = new groovy.text.SimpleTemplateEngine() 
def template = engine.createTemplate(mail_body_html).make(binding)
emailext mimeType: 'text/html', attachLog: true, body: template.toString(), compressLog: true, subject: subject, to: subscribers

}
