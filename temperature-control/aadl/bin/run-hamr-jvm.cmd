::#! 2> /dev/null                                   #
@ 2>/dev/null # 2>nul & echo off & goto BOF         #
if [ -z ${SIREUM_HOME} ]; then                      #
  echo "Please set SIREUM_HOME env var"             #
  exit -1                                           #
fi                                                  #
exec ${SIREUM_HOME}/bin/sireum slang run "$0" "$@"  #
:BOF
setlocal
if not defined SIREUM_HOME (
  echo Please set SIREUM_HOME env var
  exit /B -1
)
%SIREUM_HOME%\bin\sireum.bat slang run "%0" %*
exit /B %errorlevel%
::!#
// #Sireum

import org.sireum._


val sireum = Os.path(Os.env("SIREUM_HOME").get) / "bin" / (if(Os.isWin) "sireum.bat" else "sireum")
val AADL_DIR = Os.slashDir.up
val SLANG_DIR = AADL_DIR.up / "hamr-gen" / "slang"

def runHamr(): Unit = {

  val OSATE_HOME = Os.home / ".sireum" / "phantom" / (if(Os.isMac) "osate-2.10.2-vfinal.app/Contents/MacOS" else "osate-2.10.2-vfinal") / "osate"

  val PHANTOM_ARGS = "-nosplash -console -consoleLog --launcher.suppressErrors -data @user.home/.sireum -application org.sireum.aadl.osate.cli"

  val os = if(Os.isWin) "win" else if(Os.isLinux) "linux" else if(Os.isMac) "mac" else halt("OS not supported")
  val noRunProyekIve: String = if((sireum.up / os / "idea").exists) "" else "--no-proyek-ive"

  val CODEGEN_ARGS = s"hamr codegen ${noRunProyekIve} --output-dir ${SLANG_DIR} --package-name tc"

  println("Running HAMR Codegen via OSATE")
  proc"${OSATE_HOME} ${PHANTOM_ARGS} ${CODEGEN_ARGS} ${AADL_DIR.value}/.project".console.runCheck()
}

                                                                                                                                                                  
if(!ops.ISZOps(Os.cliArgs).contains("nohamr")) {
  runHamr()
}

val result = proc"${sireum.value} proyek logika --all --par ${SLANG_DIR.value}".console.run()

assert(result.ok, "Logika did not complete successfully: ${result.exitCode}")
