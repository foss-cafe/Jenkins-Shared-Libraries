# Jenkins Shared Libraries

## This repo includes common fucntions that are used in Jenkins pipeline

### How to Use:

- Follow https://jenkins.io/doc/book/pipeline/shared-libraries/ these instructions to setup Shared Libs

**Note**: Shared Libraries marked `Load implicitly` allows Pipelines to immediately use classes or global variables defined by any such libraries.

> If you are not using `Load implicitly`

You can call Libraries in your **Jenkinsfile** using `@Library`

Ex:

```
@Library('my-shared-library') _
/* Using a version specifier, such as branch, tag, etc */
@Library('my-shared-library@1.0') _
/* Accessing multiple libraries with one statement */
@Library(['my-shared-library', 'otherlib@abc1234']) _
```
