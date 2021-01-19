**Useless Project**

*How to*

Obtain the zip file  or clone from github to test this libgdx application.



*Reproduce the magic*

in project directory :

``gradle build ;
gradle lwjgl3:run ``

Then modify window size quite quickly 
until it decides to stretch the window to full screen


*images*

When FitViewport works :
![Caption](demoImages/displayOK_BuggyScreen.png)
When FitViewport fail  :
![Caption](demoImages/problem_BuggyScreen.png)


It always should use the defined Viewport
![Caption](demoImages/displayOK_WorkingScreen.png)
