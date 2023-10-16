# youtube-backend

youtube clone project

## WebSecurityConfig

```java
.requestMatchers(new AntPathRequestMatcher("/api/user/**")).permitAll()
.requestMatchers(new AntPathRequestMatcher("/api/public/**")).permitAll()
.anyRequest().authenticated();
```

### 사용자 ID 필요할 시

```java
@AuthenticationPrincipal String id
```

## API

- 카테고리 전체 조회 : GET / api/public/category
- 회원 가입 : POST / api/user/signup
  - JSON : id, password, name
- 로그인 : POST / api/user/signin
  - JSON : id, password
- 채널 추가 : POST / api/channel
  - form-data : name, desc, photo(file)
- 영상 추가 : POST / api/video
  - form-data : categoryCode, title, desc, image, video
- 영상 전체 조회 : GET / api/public/video
- 영상 1개 조회 : GET / api/public/video/{videoCode}
- 영상 1개에 따른 댓글 전체 조회 : GET / api/public/video/{videoCode}/comment
- 댓글 추가 : POST / api/video/comment
  - JSON : commentDesc, videoCode, (commentParent : 대댓글 작성시)
- 댓글 수정 : PUT / api/video/comment
  - JSON : commentCode, videoCode, commentDesc, (commentParent : 대댓글 수정시)
- 댓글 삭제 : DELETE / api/video/comment/{commentCode}

## JPA(Java Persistence API)

- 자바 언어로 DB에 명령을 내리는 도구
- 데이터를 객체 지향적으로 관리

### Entity(엔티티)

- 자바 객체를 DB가 이해할 수 있도록 만드는 역할
- 이를 기반으로 테이블 생성이 되기도 함
- @Entity : 이 어노테이션이 붙은 클래스를 기반으로 DB 테이블 생성 가능 (테이블이 존재하지 않을시)
- @Id : 엔티티의 대표값 (Primary Key)
- @GeneratedValue : 대표값을 자동으로 생성 (시퀀스와 관련)
- @Column : 엔티티의 대표값 이외의 값들

### Repository(레포지토리)

- 엔티티가 DB 속 테이블에 저장 및 관리할 수 있게 도와주는 인터페이스
- 대표적으로 사용하는 인터페이스 : JpaRepository
- CRUD만 사용하는 인터페이스 : CrudRepository
