@NonCPS
def call(String buildStatus = 'STARTED',String subscribers="admin@example.com"){
// buildStatus of null means successfull
  buildStatus = buildStatus ?: 'SUCCESSFUL'
  subscribers = subscribers ?: 'rajeev.jaggavarapu@srijan.net'
  def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]"
  def branchName = "${env.BRANCH_NAME}"
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
  <tr><th>Commit</th><td>${commit}</td></tr>
  <tr><th>Author:</th><td>${author}</td></tr>
  <tr><th>Commit Message:</th><td>${message}</td></tr>
</table>
 
</body>
</html>
''' 
def binding = ["buildStatus":buildStatus, "RUN_DISPLAY_URL":"${env.BUILD_URL}","JOB_NAME":"${env.JOB_NAME}","durationString":"${currentBuild.durationString}","commit":"${env.GIT_COMMIT}","author":"${env.CHANGE_AUTHOR}","message":"${env.CHANGE_TITLE}"]  
def engine = new groovy.text.SimpleTemplateEngine() 
def template = engine.createTemplate(mail_body_html).make(binding)
emailext mimeType: 'text/html', attachLog: true, body: template.toString(), compressLog: true, subject: subject, to: subscribers

}
