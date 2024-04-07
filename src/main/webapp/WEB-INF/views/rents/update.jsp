<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp" %>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>Reservations</h1>
        </section>

        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">

                        <form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/rents/update">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="car" class="col-sm-2 control-label">Voiture</label>
                                    <div class="col-sm-10">
                                        <select class="form-control" id="car" name="car">
                                            <c:forEach items="${vehicles}" var="vehicle">
                                                <option value="${vehicle.id}" ${vehicle.id == rent.vehicleId ? 'selected' : ''}>${vehicle.constructeur} ${vehicle.modele}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="client" class="col-sm-2 control-label">Client</label>
                                    <div class="col-sm-10">
                                        <select class="form-control" id="client" name="client">
                                            <c:forEach items="${clients}" var="client">
                                                <option value="${client.id}" ${client.id == rent.clientId ? 'selected' : ''}>${client.prenom} ${client.nom}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="begin" class="col-sm-2 control-label">Date de debut</label>
                                    <div class="col-sm-10">
                                        <!-- Utilisation du type 'date' pour une saisie plus facile des dates -->
                                        <input type="date" class="form-control" id="begin" name="begin" value="${rent.begin}" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="end" class="col-sm-2 control-label">Date de fin</label>
                                    <div class="col-sm-10">
                                        <input type="date" class="form-control" id="end" name="end" value="${rent.end}" required>
                                    </div>
                                </div>
                            </div>
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Modifier</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
