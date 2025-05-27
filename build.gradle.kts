plugins {
	java
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(23)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

extra["springAiVersion"] = "1.0.0-M3"

dependencies {
	// Spring Boot 기반 프로젝트용
	// OpenAI,JPA,Web기본,security,validation 라이브러리들
	implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	//SpringDoc: OpenAPI 문서 생성 및 UI 제공 라이브러리.
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.14")
	implementation("org.springdoc:springdoc-openapi-data-rest:1.6.14")

	// MySQL 데이터베이스 연결을 위한 JDBC 드라이버
	implementation("mysql:mysql-connector-java:8.0.33")


	// Retrofit: REST API 호출을 간단히 구현하기 위한 HTTP 클라이언트 라이브러리
	implementation("com.squareup.retrofit2:retrofit:2.9.0")

	// Retrofit: Gson을 사용하여 JSON 변환을 처리하기 위한 컨버터
	implementation("com.squareup.retrofit2:converter-gson:2.9.0")


	// Gson: Java 객체와 JSON 간의 직렬화/역직렬화를 처리하는 라이브러리
	implementation("com.google.code.gson:gson:2.8.8")


	implementation("com.google.api-client:google-api-client:1.30.10")
	implementation("com.google.http-client:google-http-client-jackson2:1.39.2")


	// OpenAI GPT-3 Java 클라이언트 라이브러리
	implementation("com.theokanning.openai-gpt3-java:api:0.14.0")


	// jsonwebtoken 웹인증을 위한 라이브러리
	implementation ("io.jsonwebtoken:jjwt-api:0.11.2")
	runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.2")
	runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.11.2")

	// Lombok: 코드 생성을 단순화하기 위한 라이브러리 (컴파일 시 사용)
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	//@ConfigurationProperties 메타데이터 생성 및 IDE 지원 라이브러리.
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	//개발 편의성 향상용 자동 재시작 라이브러리.
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	//테스트 라이브러리들
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

dependencyManagement {
	imports {
		mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
