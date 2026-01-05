<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Marketing Campaign Management</title>

    <!-- Bootstrap 4 -->
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script
        src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container mt-4">
    <h1 class="text-center">Marketing Campaign Management</h1>

    <!-- Add Campaign Button -->
    <div class="mb-3 text-right">
        <button class="btn btn-success" data-toggle="modal" data-target="#addCampaignModal">
            Add New Campaign
        </button>
    </div>

    <!-- Campaign Table -->
    <table class="table table-bordered table-hover">
        <thead class="table-dark">
        <tr>
            <th>Campaign ID</th>
            <th>Content</th>
            <th>Status</th>
            <th>Image</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="campaign" items="${campaigns}">
            <tr>
                <td>${campaign.campaignId}</td>
                <td>${campaign.content}</td>
                <td>
                    <c:choose>
                        <c:when test="${campaign.isDelete}">
                            <span class="badge badge-danger">Deleted</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-success">Active</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${campaign.campaignImages != null && campaign.campaignImages.size() > 0}">
                            ${campaign.campaignImages[0].imagePath}
                        </c:when>
                        <c:otherwise>
                            No Image
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <div class="d-flex justify-content-center">
                        <!-- Edit -->
                        <form action="${pageContext.request.contextPath}/admin/campaign/editCampaign"
                              method="get" class="mr-1">
                            <input type="hidden" name="campaignId" value="${campaign.campaignId}">
                            <input type="hidden" name="content" value="${campaign.content}">
                            <input type="hidden" name="image"
                                   value="${campaign.campaignImages != null && campaign.campaignImages.size() > 0
                                           ? campaign.campaignImages[0].imagePath : ''}">
                            <button class="btn btn-warning btn-sm">Edit</button>
                        </form>

                        <!-- Delete -->
                        <form action="${pageContext.request.contextPath}/admin/campaign/deleteCampaign"
                              method="post">
                            <input type="hidden" name="campaignId" value="${campaign.campaignId}">
                            <button class="btn btn-danger btn-sm"
                                    onclick="return confirm('Are you sure you want to delete this campaign?');">
                                Delete
                            </button>
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>

        <c:if test="${empty campaigns}">
            <tr>
                <td colspan="5" class="text-center">No campaigns available!</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>

