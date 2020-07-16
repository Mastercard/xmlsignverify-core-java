@Library('zapp-utilities@features/S1312905') _

continuousBuildBootUtility {
    maven_v = 'Maven 3.6.1'
    java_v = 'openjdk11.28'
    //build_goal = 'clean install -Pdev-env-ci,dev-env-local,jenkins,release-app -DskipTests'
    //test_goal = 'test -Pdev-env-ci,jenkins -Djacoco.reportPath.dir=$WORKSPACE/target'
	build_goal = 'clean install -Pjenkins -DskipTests'
    test_goal = 'test -Pjenkins -Djacoco.reportPath.dir=$WORKSPACE/target'
	//it_test_goal = 'clean install test -Pe2e-tests,jenkins,dev-env-local,test-ci -Dtest.profile=ci -e -rf :itest'
    //sonar_goal = 'sonar:sonar -Psonar,dev-env-ci -Dsonar.projectKey=ap-bah-crypto -Dsonar.login=${sonar_credentials} -Dsonar.branch.name=$BRANCH_NAME'
    target = 'develop'
    branch = 'develop'
    env = 'cb'
    component = 'ap-bah-crypto-utility'
    agent = 'zapp-dev-env2'
    lock_db = ''
}