##################################################
#####             引用APP包                  #####
##################################################
###一：授权码模式（因为使用的自定义登录，修改了登录页面，所以这个就不能用了哦）
    1：URL： http://localhost:8080/oauth/authorize?client_id=imooc&response_type=code&redirect_uri=http://www.baidu.com
    2：输入账号密码登录系统
    3：点击授权按钮
    4：系统自动回调到 https://www.baidu.com/?code=Mcoq39
    5: 根据code换token 
        URL：（POST） http://localhost:8080/oauth/token?grant_type=authorization_code&code=cPKGcO&client_id=imooc&client_secret=imoocsecret&redirect_uri=http://www.baidu.com
    6：返回结果
        {
            "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJyb290Iiwic2NvcGUiOlsiYWxsIl0sImNvbXBhbnkiOiJiZmQiLCJleHAiOjE1NjU5OTg5NjksImF1dGhvcml0aWVzIjpbInh4eCJdLCJqdGkiOiJiZjE0OWYzYS1iMzc1LTQyZmEtODgzOC0wYTBhNDUxZDBkNGQiLCJjbGllbnRfaWQiOiJpbW9vYyJ9.HobbkJZVqnfTctgi57Gg5w5vWVVaAfw0uSp1CtyVOSk",
            "token_type": "bearer",
            "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJyb290Iiwic2NvcGUiOlsiYWxsIl0sImF0aSI6ImJmMTQ5ZjNhLWIzNzUtNDJmYS04ODM4LTBhMGE0NTFkMGQ0ZCIsImNvbXBhbnkiOiJiZmQiLCJleHAiOjE1Njg1MTg5NjksImF1dGhvcml0aWVzIjpbInh4eCJdLCJqdGkiOiJiZTAyNDYwNC04OWE2LTQzOWEtOTRmOS0zNzMxZjFlOGE3MzUiLCJjbGllbnRfaWQiOiJpbW9vYyJ9.3ocMkWFzEZ7bzppa638xat5PnqvubIsIEquJFl02K4o",
            "expires_in": 71999,
            "scope": "all",
            "company": "bfd",
            "jti": "bf149f3a-b375-42fa-8838-0a0a451d0d4d"
        }
           
###二：使用imagecode token的形式
    1：URL http://localhost:8080/code/image?width=100  
        headers:  deviceId=xxx    一定要带上设备ID
    2：根据获取到的图片验证码换token
       URL:http://localhost:8080/authentication/form
           headers:
               Authorization: Basic aW1vb2M6aW1vb2NzZWNyZXQ=   (client-id & client-secret basic加密)
               deviceId: xxx
               Content-Type: application/x-www-form-urlencoded
           body:
               username: admin
               password: 123456
               imageCode: 575088(这个是上一个接口获取到的)
    3:返回token
        {
            "access_token": "5bd61754-53d9-47c0-8469-578021c4e43b",
            "token_type": "bearer",
            "refresh_token": "05a25121-8a1e-4041-8abd-974fa2c5e6cd",
            "expires_in": 357,
            "scope": "all"
        }

###三：使用smsCode token的形式
    1：URL http://127.0.0.1:8080/code/sms?mobile=18102460000
        headers:
                deviceId=xxx
    2：使用短信验证码登录
        http://127.0.0.1:8080/authentication/mobile
        headers:
           Authorization: Basic aW1vb2M6aW1vb2NzZWNyZXQ=   (client-id & client-secret basic加密)
           deviceId: xxx
           Content-Type: application/x-www-form-urlencoded
       body:
           mobile: 18102460000
           smsCode: 123456
    3:返回token      
       {
           "access_token": "b2ecea8e-1a88-4b1b-8031-3acd3ea4cb86",
           "token_type": "bearer",
           "refresh_token": "329ff9e7-711f-4061-b379-925fce69df96",
           "expires_in": 71999,
           "scope": "all"
       }
       
###四：令牌刷新（无感知获）
    1：URL http://localhost:8080/oauth/token
         headers:
            Authorization: Basic aW1vb2M6aW1vb2NzZWNyZXQ=   (client-id:client-secret basic64加密)
         body:
            grant_type:refresh_token
            refresh_token:XXX(秘钥获取的refresh_token的值)
            scope:all
    2：返回新的token
        {
            "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiIxODEwMjQ2NjMzMCIsInNjb3BlIjpbImFsbCJdLCJjb21wYW55IjoiYmZkIiwiZXhwIjoxNTY2MTcxNTc3LCJhdXRob3JpdGllcyI6WyJ4eHgiXSwianRpIjoiYzFmNTM3MDMtMWE3MS00NjE1LWJjODItNTc1NDg0YTMxYjFkIiwiY2xpZW50X2lkIjoiaW1vb2MifQ.7Op9RzyPKQF-TKLNyVRyssufoLvC0KxopsxViCG2vvc",
            "token_type": "bearer",
            "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiIxODEwMjQ2NjMzMCIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiJjMWY1MzcwMy0xYTcxLTQ2MTUtYmM4Mi01NzU0ODRhMzFiMWQiLCJjb21wYW55IjoiYmZkIiwiZXhwIjoxNTY4NjkxMzM3LCJhdXRob3JpdGllcyI6WyJ4eHgiXSwianRpIjoiM2UzZDdlYmUtZGVjZi00ZWY5LTk4ZTQtZmY0MmZhMWEyYzk5IiwiY2xpZW50X2lkIjoiaW1vb2MifQ.z1SKhhBMAnH3oXc8llYNcXfRTeHaFKtipAJ-LbAuhYU",
            "expires_in": 71999,
            "scope": "all",
            "company": "bfd",
            "jti": "c1f53703-1a71-4615-bc82-575484a31b1d"
        }