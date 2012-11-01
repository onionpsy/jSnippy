jSnippy
=======
jSnippy is a small tool that help taking screenshoot. You juste have to select to take what you want. It's quite simple !

Info
#####
It works pretty well on Windows XP/Vista/7.
there are still some bug that appears on different version of Linux. I'm on it.
Not yet tested on Mac OSX.

Requirement
##########
 * Java 1.6.0 or greater
 * Composite installed and enabled in Xorg config.

Troubleshooting
###############
FOR LINUX ONLY : 
The tool is automaticly trying to take the system default GUI
If you haven't modified. It will takes the 'metal' look an feel.
You can modify this with this command (add in you .bashrc) : 
```bash
	export _JAVA_OPTIONS='-Dawt.useSystemAAFontSettings=on -Dswing.aatext=true -Dswing.defaultlaf=com.sun.java.swing.plaf.gtk.GTKLookAndFeel'
```

Better now ? :)


