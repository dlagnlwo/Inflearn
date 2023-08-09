<%@ page import="java.util.List" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    MemberRepository memberRepository = MemberRepository.getInstance();
    List<Member> members = memberRepository.findAll();
%>

<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
</head>
<body>
<a href="/index.html">메인</a>

<table>
    <thead>
        <th>id</th>
        <th>username</th>
        <th>age</th>
    </thead>

    <tbody>
    <%
        for(Member member : members) {
            out.write("<tr>");
            out.write("     <td>" + members.getId() + "</td>");
            out.write("     <td>" + members.getUsername() + "</td>");
            out.write("     <td>" + members.getAge() + "</td>");
            out.write("</tr>");
        }
    </tbody>
</table>
</body>
</html>