# CJ 알림톡 서비스 (cj_alimtalk_service)

Spring Boot 기반 알림톡 발송 서비스입니다.  
매일 지정 시각(9시 21분)에 DB에 등록된 대상자(OldMan)를 조회해 알림톡 API로 전송합니다.

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

```bash
# 의존성 설치 및 부팅
./gradlew bootRun
```

Windows:

```cmd
gradlew.bat bootRun
```

---

## 설정

### 1. DB (application.yml)

`src/main/resources/application.yml`에서 데이터소스를 설정합니다.

```yaml
spring:
  datasource:
    url: jdbc:mysql://호스트:포트/DB명?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul
    username: 사용자명
    password: 비밀번호
    driver-class-name: com.mysql.cj.jdbc.Driver
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
├── CjAlimtalkServiceApplication.java   # 진입점, @EnableScheduling
├── config/
│   └── RestTemplateConfig.java        # RestTemplate 빈
└── alimtalk/
    ├── entity/
    │   └── OldMan.java                # kw_oldman_sms 테이블 매핑
    ├── repository/
    │   └── OldManRepository.java      # OldMan 조회 (crtdt 기준)
    ├── service/
    │   ├── OldManService.java         # 오늘 등록분 조회 → ReceiverDto 변환
    │   ├── AlimSendService.java       # 알림톡 API 요청 조립 및 전송
    │   └── OldManSchedular.java       # 매일 9:21 실행 스케줄러
    └── dto/response/
        ├── AlimApiRequestDto.java     # API 최상위 요청 (link_id, link_token, data)
        ├── AlimApiDataDto.java        # data 블록 (callback, receiver 등)
        └── ReceiverDto.java           # 수신자 1명 (dstaddr, text, 템플릿 변수)
```

---

## 동작 흐름

1. **스케줄러** (`OldManSchedular.runAtNineTwentyOne`)  
   - 매일 **9시 21분**에 실행됩니다.

2. **데이터 조회** (`OldManService.getReceiversForAlim`)  
   - **오늘 00:00 이후** `crtdt`로 등록된 `OldMan`을 조회합니다.  
   - 각 건을 알림톡 API 수신자 포맷(`ReceiverDto`)으로 변환합니다.

3. **알림톡 전송** (`AlimSendService.sendAlim`)  
   - 설정(application-secret)에서 link_id, link_token, callback 등 읽어  
   - `AlimApiRequestDto`로 조립 후 **RestTemplate**으로 API `base-url`에 POST 전송합니다.  
   - 전송 실패 시 예외는 로그만 남기고, 스케줄러는 정상 종료됩니다.

---

## DB 테이블

알림 발송 대상은 **kw_oldman_sms** 테이블을 사용합니다.

- **crtdt**: 등록일시. “오늘 하루” 조회 시 이 필드 기준으로 `crtdt >= 오늘 00:00` 조건을 사용합니다.
- 수신번호 매핑: `mgrphone`(또는 `mgrtel`) → API의 `dstaddr`, `cname` → `opt_name` 등은 `OldManService.toReceiverDto`에서 설정됩니다.

---

## 라이선스

내부/예제 용도로 사용되는 프로젝트입니다.
