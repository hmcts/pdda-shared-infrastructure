<%@ page import="java.io.PrintWriter"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html lang="en">

	<head>
        <title><%=exception%></title>
        <link rel="stylesheet" type="text/css" href="css/general.css"/>
        <link rel="stylesheet" type="text/css" href="css/table.css"/>
		<script type="text/javascript" src="js/browser_sniffer.js">
		</script>
        <script type="text/javascript" src="js/common.js">
            </script>
    </head>

	<body onLoad="if(window.self == window.top.lhs.buffer) {top.rhs.location.href=self.location.href;}">
	<table aria-describedby="Banner" cellpadding="0" cellspacing="0" width="100%" border="0">
		<th>
		<tr class="header-image" height="58" valign="bottom" style="background-image: url(header/images/banner.gif?x=300&y=50&size=50&text=%20&width=1600)">
		<td>&nbsp;</td>
		</tr>
	</table>
	<table aria-describedby="PageInfo" class="navbar" width="100%" border="0" cellspacing="0" cellpadding="0">
		<th>
		<tr>
		<td align="left" width="33.33%" class="last-updated-date">&nbsp;</td>
		<td align="center" width="33.33%" class="list-info" id="listInfo">&nbsp;</td>
		<td align="right" width="33.33%" class="pageInfo"><div align="right" id="pageInfo">Page 1 of 1</div></td>
		<td width="10">&nbsp;</td>
		</tr>
	</table>

		<div class="no-information"> No Information To Display </div>


        <!-- Mouseover the two colored bars to see the stack trace.-->

        <div id="stackTrace" class="stack-trace">

                Stack Trace:
            <%
                exception.printStackTrace(new PrintWriter(out));
            %>


        </div>

        <div onMouseOver="stackTrace.style.visibility='visible'" onMouseOut="stackTrace.style.visibility='hidden'" class="error-exception-tag"/>

		<pre id="log"></pre>

	</body>

</html>
