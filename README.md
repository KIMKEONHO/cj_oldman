# CJ 알림톡 서비스 (cj_alimtalk_service)

Spring Boot 기반 알림톡 발송 서비스입니다.  
앱 기동 시 **한 번** 오늘 등록된 대상자(OldMan)를 조회해 알림톡 API로 전송한 뒤 종료합니다.  
**리눅스 cron**과 함께 사용해 매일 정해진 시간에만 실행하면 서버 리소스를 절약할 수 있습니다.

---

## 기술 스택

- **Java 17**
- **Spring Boot 4.0.3**
- **Spring Data JPA** · **Spring Web (MVC)**
- **MySQL** (Connector/J)
- **Lombok**
- **Gradle**

---

## 요구 사항

- JDK 17 이상
- MySQL 8.x (또는 호환 버전)
- 알림톡 API 연동 정보 (base-url, link-id, link-token 등)

---

## 실행 방법

### 로컬에서 한 번 실행 (개발/테스트)

```bash
./gradlew bootRun
```

Windows:

```cmd
gradlew.bat bootRun
```

웹 서버는 띄우지 않으며(`web-application-type: none`), 기동 시 **CommandLineRunner**가 알림톡 발송 로직을 한 번 실행한 뒤 프로세스가 종료됩니다.

### JAR 빌드 후 실행 (운영/cron)

```bash
# JAR 빌드
./gradlew bootJar

# 실행 (웹 서버 없이, 작업 1회 수행 후 종료)
java -jar build/libs/cj_alimtalk_service-0.0.1-SNAPSHOT.jar
```

---

## 리눅스 cron으로 정해진 시간에 실행

앱을 상시 구동하지 않고, **cron이 정해진 시간에 JAR를 한 번 실행**하는 방식으로 사용할 수 있습니다.

1. 빌드한 JAR를 서버에 복사 (예: `/opt/cj_alimtalk/`)
2. `application.yml`, `application-secret.yml` 등 설정이 JAR와 같은 위치 또는 classpath에 오도록 구성
3. cron 등록

**예: 매일 9시 21분에 실행**

```bash
# crontab -e 로 편집
21 9 * * * cd /opt/cj_alimtalk && java -jar cj_alimtalk_service-0.0.1-SNAPSHOT.jar >> /var/log/cj_alimtalk.log 2>&1
```

실행 스크립트를 두고 cron에서는 스크립트만 호출해도 됩니다.

```bash
# /opt/cj_alimtalk/run.sh
#!/bin/bash
cd /opt/cj_alimtalk
java -jar cj_alimtalk_service-0.0.1-SNAPSHOT.jar
```

```cron
21 9 * * * /opt/cj_alimtalk/run.sh >> /var/log/cj_alimtalk.log 2>&1
```

---

## 설정

### 1. DB (application.yml)

`src/main/resources/application.yml`에서 데이터소스를 설정합니다.

```yaml
spring:
  application:
    name: cj_alimtalk_service
  datasource:
    url: jdbc:mysql://호스트:포트/DB명?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul
    username: 사용자명
    password: 비밀번호
    driver-class-name: com.mysql.cj.jdbc.Driver
  main:
    web-application-type: none   # 웹 서버 미기동 (cron 실행용)
  config:
    import: optional:classpath:application-secret.yml
```

실제 DB URL·계정은 환경에 맞게 수정하세요.

### 2. 알림톡 API (application-secret.yml)

API 키·URL 등은 **application-secret.yml**에 두며, 이 파일은 `.gitignore` 대상입니다.

1. `src/main/resources/application-secret.yml.example`을 복사해  
   `src/main/resources/application-secret.yml`로 저장합니다.
2. 아래 항목을 실제 값으로 채웁니다.

| 키 | 설명 |
|----|------|
| `alim.api.base-url` | 알림톡 API 베이스 URL |
| `alim.api.link-id` | API 링크 아이디 |
| `alim.api.link-token` | API 링크 토큰 |
| `alim.data.callback` | 발신번호 |
| `alim.data.dept-no` | 부서번호 |
| `alim.data.msg-type` | `kko_a`(알림톡) 또는 `kko_f`(친구톡) |
| `alim.data.k-template-code` | 알림톡 템플릿 코드 |
| `alim.data.kakao-dsptch-profl` | 카카오 발신 프로필 (@프로필명) |
| `alim.data.k-next` | 발송 실패 시 대체 발송: `1` 또는 `2` |

---

## 프로젝트 구조

```
src/main/java/com/example/cj_alimtalk_service/
├── CjAlimtalkServiceApplication.java   # 진입점
├── config/
│   └── RestTemplateConfig.java         # RestTemplate 빈
└── alimtalk/
    ├── entity/
    │   └── OldMan.java                 # kw_oldman_sms 테이블 매핑
    ├── repository/
    │   └── OldManRepository.java       # OldMan 조회 (crtdt 기준)
    ├── service/
    │   ├── OldManService.java          # 오늘 등록분 조회 → ReceiverDto 변환
    │   └── AlimSendService.java        # 알림톡 API 요청 조립 및 전송
    ├── runner/
    │   └── AlimJobRunner.java          # 기동 시 1회 실행 (CommandLineRunner)
    └── dto/response/
        ├── AlimApiRequestDto.java      # API 최상위 요청 (link_id, link_token, data)
        ├── AlimApiDataDto.java         # data 블록 (callback, receiver 등)
        └── ReceiverDto.java            # 수신자 1명 (dstaddr, text, 템플릿 변수)
```

---

## 동작 흐름

1. **앱 기동**  
   - `java -jar ...` 또는 `bootRun`으로 실행합니다.  
   - `spring.main.web-application-type: none`으로 웹 서버는 띄우지 않습니다.

2. **AlimJobRunner** (CommandLineRunner)  
   - Spring Boot가 컨텍스트 준비 후 **자동으로 한 번** `run()`을 호출합니다.  
   - `OldManService.getReceiversForAlim()` → 오늘 00:00 이후 등록된 `OldMan` 조회 후 `ReceiverDto` 리스트로 변환  
   - `AlimSendService.sendAlim(receivers)` → API 포맷으로 조립 후 POST 전송  
   - `System.exit(0)` 로 프로세스 종료

3. **데이터 조회** (`OldManService.getReceiversForAlim`)  
   - **오늘 00:00 이후** `crtdt`로 등록된 `OldMan`을 조회합니다.  
   - 각 건을 알림톡 API 수신자 포맷(`ReceiverDto`)으로 변환합니다.

4. **알림톡 전송** (`AlimSendService.sendAlim`)  
   - 설정(application-secret)에서 link_id, link_token, callback 등 읽어  
   - `AlimApiRequestDto`로 조립 후 **RestTemplate**으로 API `base-url`에 POST 전송합니다.  
   - 전송 실패 시 예외는 로그만 남깁니다.

---

## DB 테이블

알림 발송 대상은 **kw_oldman_sms** 테이블을 사용합니다.

- **crtdt**: 등록일시. “오늘 하루” 조회 시 이 필드 기준으로 `crtdt >= 오늘 00:00` 조건을 사용합니다.
- 수신번호 매핑: `mgrphone`(또는 `mgrtel`) → API의 `dstaddr`, `cname` → `opt_name` 등은 `OldManService.toReceiverDto`에서 설정됩니다.

---

## 라이선스

내부/예제 용도로 사용되는 프로젝트입니다.
