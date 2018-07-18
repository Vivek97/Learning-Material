Set wshShell = Wscript.CreateObject("WScript.Shell")

If WScript.Arguments.Count = 1 Then
                Wscript.Echo "RunningSystemAwake Process..."
End If

While True
                WshShell.SendKeys "{NUMLOCK}"
                WshShell.SendKeys "{NUMLOCK}"
                WScript.Sleep 60000
WEnd
