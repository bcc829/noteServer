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
~	'password': 'password'~
~}~

~response~

~Body :~
~{~
~	'token': 'token'~
~}~

~-사용자 정보-~
~Get : http://URL/api/v1/member~

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


인증 서버를 만들어 인증 방식을 oauth2.0으로 수정 하였습니다. 
로그인과 회원가입은 인증서버에서 지원 하기 때문에 노트서버에는 해당 기능이 제거될 예정입니다.

모든 요청에는 인증 서버에서 받은 access_token을 
요청 header에 Authorization: Bearer (access_token) 으로 넣어야 합니다. 
ex) 

Header:

{

	"Authorization": "Bearer (access_token)"
	
}

-사용자 정보-
Get : http://URL/api/v1/member

requset

Header:

{

	"Authorization": "Bearer (access_token)"
	
}

response

Body:

{
    "id": "jyjeong",
	
    "nickname": "sadasd",
	
    "email": "abcdefssk23@gmail.com"
	
}

-사용자 정보 수정- 

put : http://URL/api/v1/member

address, email, nickname, password, phone_number 중 하나만 수정 하거나 여러개를 같이 수정 할 수 있습니다.

requset

Header:

{

	"Authorization": "Bearer (access_token)"
	
}

Body: 

{
	"address" : "your_address",
	
	"email" : "your_email",

	"nickname": "your_nickname",
	
	"password" : "your_password",
	
	"phone_number" : "your_phone_nmber"

} 

response

Body:

{
    "id": "jyjeong",
	
    "nickname": "sadasd",
	
    "email": "abcdefssk23@gmail.com"
	
}


-회원 탈퇴-

Delete : http://URL/api/v1/member

requset

Header:

{

	"Authorization": "Bearer (access_token)"
	
}

response

Body:

{

	"success_message" : "Withdrawal Success"
	
}


-게시글 조회(페이징)-

Get: http://URL/api/v1/posts?page=(page_number)&size=(page_size - default size : 20, max size: 50)&type=(검색조건 - REG_ID, TITLE, CONTENT, TITLE_OR_CONTENT, ALL)&value=(검색어)

requset

Header:

{

	"Authorization": "Bearer (access_token)"
	
}

response

Body:

{

    "content": [
	
        {
		
            "seqId": 8,
			
            "title": "test2",
			
            "content": "test\r\ncontent",
			
            "regDate": "2019-03-22T01:15:35.385+0000",
			
            "updDate": null,
			
            "delDate": null,
			
            "regId": "정3",
			
            "readCount": 0,
			
            "deleteFlag": false
			
        }
		
    ],
	
    "pageable": {
	
        "sort": {
		
            "sorted": false,
			
            "unsorted": true,
			
            "empty": true
			
        },
		
        "offset": 0,
		
        "pageNumber": 0,
		
        "pageSize": 2,
		
        "paged": true,
		
        "unpaged": false
		
    },
	
    "totalPages": 1,
	
    "last": true,
	
    "totalElements": 1,
	
    "size": 2,
	
    "number": 0,
	
    "first": true,
	
    "sort": {
	
        "sorted": false,
		
        "unsorted": true,
		
        "empty": true
		
    },
	
    "numberOfElements": 1,
	
    "empty": false
	
}

-게시글 조회(1건)-

Get : http://URL/api/v1/post/{seqId}

requset

Header:

{

	"Authorization": "Bearer (access_token)"
	
}

response

Body:

{

    "seqId": 8,
	
    "title": "test2",
	
    "content": "test\r\ncontent",
	
    "regDate": "2019-03-22T01:15:35.385+0000",
	
    "updDate": null,
	
    "delDate": null,
	
    "regId": "정3",
	
    "readCount": 1,
	
    "deleteFlag": false
	
}

-게시글 추가-

Post : http://URL/api/v1/post

requset

Header:

{

	"Authorization": "Bearer (access_token)"
	
}

Body:

{
	
	 "title": "test2",
	 
	 "content": "test\r\ncontent"
 
}

response

Body:

{

    "seqId": 8,
	
    "title": "test23",
	
    "content": "test\r\ncontent",
	
    "regDate": "2019-03-22T01:15:35.385+0000",
	
    "updDate": null,
	
    "delDate": null,
	
    "regId": "정3",
	
    "readCount": 1,
	
    "deleteFlag": false
	
}

-게시글 수정-

Put : http://URL/api/v1/post/{seqId}

requset

Header:

{

	"Authorization": "Bearer (access_token)"
	
}

Body:

{
	
	 "title": "test23",
	 
	 "content": "test\r\ncontent"
 
}
	
response

Body:

{

    "seqId": 8,
	
    "title": "test23",
	
    "content": "test\r\ncontent",
	
    "regDate": "2019-03-22T01:15:35.385+0000",
	
    "updDate": null,
	
    "delDate": null,
	
    "regId": "정3",
	
    "readCount": 1,
	
    "deleteFlag": false
	
}

-게시글 삭제- 

Delete : http://URL/api/v1/post/{seqId}

request

Header:

{

	"Authorization": "Bearer (access_token)"
	
}

response

Body:

{

	"success_message", "Post delete success"
	
}


#TODO
댓글 작성, 게시글 파일 첨부 및 다운로드 : 기능 구현은 되어있으나 아직 문서화 작업을 하지 않음