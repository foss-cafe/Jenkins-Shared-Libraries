def call(channel,message){
    slackSend channel: "${channel}", message: "${message}"
}