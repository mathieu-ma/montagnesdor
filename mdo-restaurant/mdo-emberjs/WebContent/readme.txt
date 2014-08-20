In index.htm:
Create a div(or body) with id main-application.
Add handlebars template named application.
Add handlebars render helper for header.

In main.js:
Create a emberjs application.

In router.js:
Create a map route.

In view files:
Create a application view linked to emberjs application and initialize whatever in it.

A) Render Header:
In "application" handlebars, there is a {{render}} helper that render the "header" template.
So create HeaderController and HeaderView (not mandatory).
In the "header" template, we want to render "headerDateTime" template.
So create HeaderDateTimeController and HeaderDateTimeView(not mandatory).
In the "headerDateTime" template, just display a "dateTime" view.
When clicking on the dateTime view then open the date time dialog.
So create a route dateTimeDialog with sub-route open and close 
 
