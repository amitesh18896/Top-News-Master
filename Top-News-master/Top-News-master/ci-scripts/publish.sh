#!/usr/bin/env bash
# path of directory where APK is built
FILE_PATH="app/build/outputs/apk/riderdemo/debug"
# pattern of APK file to be upload
FILE_NAME="shipsy-*-riderdemo-debug.apk"
FILE_TO_SEND=$(find ${FILE_PATH} -name ${FILE_NAME})
# Slack channel to upload APK to
SLACK_CHANNEL="#generate-android-apps"
# If we need some additional comments
# dont change this variable
merge_message=$(echo ${merge_message})
# refinedMergeMessage=$(echo $merge_message | sed -e "s|\"|\\\"|" -e "s|*|-|g")
message=$(/usr/bin/node -p "require('html-to-mrkdwn')('$merge_message').text")
COMMENT="Merge Message:\`\`\`${message}\`\`\`"
$UTILITIES_DIR/generic_file_upload_slack.sh -c ${SLACK_CHANNEL} -f ${FILE_TO_SEND} -s ${JENKINS_OAUTH_TOKEN} -x "${COMMENT}"


FILE_PATH_DEV="app/build/outputs/apk/riderdev/debug"
# pattern of APK file to be upload
FILE_NAME_DEV="shipsy-*-riderdev-debug.apk"
DEV_FILE_TO_SEND=$(find ${FILE_PATH_DEV} -name ${FILE_NAME_DEV})
# Slack channel to upload APK to
SLACK_CHANNEL="#generate-android-apps"
# If we need some additional comments
# dont change this variable
merge_message=$(echo ${merge_message})
# refinedMergeMessage=$(echo $merge_message | sed -e "s|\"|\\\"|" -e "s|*|-|g")
message=$(/usr/bin/node -p "require('html-to-mrkdwn')('$merge_message').text")
COMMENT="Merge Message:\`\`\`${message}\`\`\`"
$UTILITIES_DIR/generic_file_upload_slack.sh -c ${SLACK_CHANNEL} -f ${DEV_FILE_TO_SEND} -s ${JENKINS_OAUTH_TOKEN} -x "${COMMENT}"
