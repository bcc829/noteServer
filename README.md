# noteServer
노트 프로젝트 서버

~현재 인증 서버가 미완성이므로 기본 인증(Basic authentication)을 사용하고 있으나~
~추후 인증 서버를 만들면 인증 방식을 oauth2.0으로 수정할 예정입니다.~

~-로그인~
~Post : http://URL/api/v1/login~

~requset~

~Header:~
~{~
~	'Content-Type': 'application/json'~
~}~
~Body:~
~{~
~	'id': 'userId',~
	~'password': 'password'~
~}~

~response~

~Body :~
~{
~	'token': 'token'~
~}~

-사용자 정보~
Get : http://URL/api/v1/member

~헤더 Authorization에 Basic (로그인 후 받아온 토큰)을 넣어야 합니다. ex) Basic amVvbmczNDU6MzQ1Njc4~
~그렇지 않은 경우 비인가된 사용자로 인식하여 http 401에러가 나옵니다~

~requset~

~Header:~
~{
~	'Content-Type': 'application/json',~
~	'Authorization': Basic token~
~}~

~response~

~Body :~
~{~
~	'seqId': 'seqId',~
~	'id' : 'userId',~
~	'password' : 'password',~
~	'phoneNumber' : 'phoneNumber',~
~	'address' : 'address',~
~	'nickName' : 'nickName',~
~	'email': 'email',~
~	'regDate' : 'regDate'~
~}~

~-회원가입~
~Post: http://URL/api/v1/join~

~requset~

~Header:~
~{~
~	'Content-Type': 'application/json'~
~}~
~Body:~
~{~
~	'seqId': 'seqId',~
~	'id' : 'userId',~
~	'password' : 'password',~
~	'phoneNumber' : 'phoneNumber',~
~	'address' : 'address',~
~	'nickName' : 'nickName',~
~	'email': 'email'~
~}~

~response~
~{~
~}~

~-회원 정보 수정~
~Put : http://URL/api/v1/member~

~requset~

~Header:~
~{~
~	'Content-Type': 'application/json',~
~	'Authorization': Basic token~
~}~

~Body :~
~{~
~	'id' : 'userId',~
~	'password' : 'password',~
~	'phoneNumber' : 'phoneNumber',~
~	'address' : 'address',~
~	'nickName' : 'nickName',~
~	'email': 'email'~
~}~
~response~

~Body :~
~{~
~	'seqId': 'seqId',~
~	'id' : 'userId',~
~	'password' : 'password',~
~	'phoneNumber' : 'phoneNumber',~
~	'address' : 'address',~
~	'nickName' : 'nickName',~
~	'email': 'email',~
~	'regDate' : 'regDate'~
~}~

~-회원 탈퇴~
~Delete: http://URL/api/v1/member~

~requset~

~Header:~
~{~
~	'Content-Type': 'application/json',~
~	'Authorization': Basic token~
~}~

~response~

~Body :~
~{~
~}~

#TODO
게시글 작성, 댓글 작성, 게시글 파일 첨부 및 다운로드 : 기능 구현은 되어있으나 아직 문서화 작업을 하지 않음

인증 서버를 만들어 인증 방식을 oauth2.0으로 수정 하였습니다. 
로그인과 회원가입은 인증서버에서 지원 하기 때문에 노트서버에는 해당 기능이 제거될 예정입니다.
