<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
    <s:head theme="ajax" debug="true"/>
  </head>
  <body>
  <table border="1" width="50%">
    <tr>
    <td width="100%">

    <s:tabbedPanel id="test" >

      <s:div id="one" label="Tab 1" theme="ajax" labelposition="top" >
        This is the first panel.
        RoseIndia.nt<br>
        JavaJazzUp.com<br>
        NewsTrackIndia.com
      </s:div>

      <s:div id="two" label="Tab 2" theme="ajax">
        This is the second panel.
      </s:div>
      
      <s:div id="three" label="Tab 3" theme="ajax">
        This is the third panel.<br>
        Java Tutorial<br>
        PHP Tutorial<br>
        Linux Tutorial
      </s:div>

      <s:div id="four" label="Tab 4" theme="ajax">
        This is the forth panel.
      </s:div>

     </s:tabbedPanel>
     </td>
    </tr>
  </table>
  </body>
</html>