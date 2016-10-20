stage('Prepare') {
	node {
		checkout scm
	}
}

stage('Build') {
	node {
	    gradle 'clean'
	    gradle ':postman:assemble :postman-compiler:jar :postman-annotations:jar'
	}
}

stage('Test') {
	node {
 		gradle 'test'
		junit '*/build/**/TEST*.xml'
	}
}

stage('QA') {
	node {
 		gradle 'lint checkstyle'
		androidLint pattern: '*/build/**/lint-results*.xml'
		step([$class: 'CheckStylePublisher', pattern:'*/build/**/checkstyle/checkstyle.xml'])
	}
}

stage('Package') {
	node {
	    gradle 'assemble'
	}
}

stage('Archive') {
	node {
		archive '*/build/libs/*.jar'
	}
}


def gradle(def tasks) {
    sh "./gradlew ${tasks}"
}

def archive(def filename) {
	archiveArtifacts artifacts: "${filename}",
		caseSensitive: false, defaultExcludes: false, onlyIfSuccessful: true
}
