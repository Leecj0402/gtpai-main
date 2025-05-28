plugins {
	id 'java'
	id 'org.springframework.boot' version '3.4.5'
	id 'io.spring.dependency-management' version '1.1.7'
}

group = 'com.example'
version = '0.0.1-SNAPSHOT'

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(23)
	}
}

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

ext {
	set('springAiVersion', "1.0.0-M8")
}

dependencies {
	// Spring Boot Starters
	implementation 'org.springframework.boot:spring-boot-starter-data-elasticsearch'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-security'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-web-services'
	implementation 'org.springframework.boot:spring-boot-starter-webflux'
	implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'

	// 외부 라이브러리
	implementation 'org.jsoup:jsoup:1.17.2'
	implementation 'org.seleniumhq.selenium:selenium-java:4.20.0'
	implementation 'com.google.api-client:google-api-client:2.2.0'
	implementation 'com.google.http-client:google-http-client-jackson2:1.43.3'

	// JWT
	implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
	runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.5'
	runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.11.5'

	// 개발/컴파일 도구
	compileOnly 'org.projectlombok:lombok'
	developmentOnly 'org.springframework.boot:spring-boot-devtools'
	runtimeOnly 'com.mysql:mysql-connector-j'
	annotationProcessor 'org.projectlombok:lombok'

	// 테스트
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'io.projectreactor:reactor-test'
	testImplementation 'org.springframework.security:spring-security-test'
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
	}
}

tasks.named('test') {
	useJUnitPlatform()
}
