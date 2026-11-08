param(
    [string]$JavaHome = $env:JAVA_HOME,
    [string]$Libraries = (Join-Path $env:APPDATA 'ModrinthApp/meta/libraries'),
    [switch]$ClassesOnly
)
$ErrorActionPreference='Stop'
if (-not $JavaHome) {
    $javac = (Get-Command javac.exe -ErrorAction Stop).Source
    $JavaHome = Split-Path -Parent (Split-Path -Parent $javac)
}
# Build into a fresh stage so the JAR contains only this invocation's outputs.
$stageName = [Guid]::NewGuid().ToString('N')
$b = Join-Path (Join-Path $PSScriptRoot '.build') $stageName
New-Item -ItemType Directory -Force "$b/classes","$b/tools" | Out-Null
function Checked([string]$program,[string[]]$arguments){& $program @arguments;if($LASTEXITCODE){throw "Build failed: $program ($LASTEXITCODE)"}}
$gson="$Libraries/com/google/code/gson/gson/2.13.2/gson-2.13.2.jar"
$loader="$Libraries/net/fabricmc/fabric-loader/0.19.3/fabric-loader-0.19.3.jar"
$mixin="$Libraries/net/fabricmc/sponge-mixin/0.17.3+mixin.0.8.7/sponge-mixin-0.17.3+mixin.0.8.7.jar"
$asm="$Libraries/org/ow2/asm/asm/9.10.1/asm-9.10.1.jar"
$sources=Get-ChildItem "$PSScriptRoot/src" -Filter *.java | Where-Object Name -ne 'GenerateScreens.java' | ForEach-Object FullName
Checked "$JavaHome/bin/javac.exe" (@('-proc:none','-encoding','UTF-8','-source','8','-target','8','-cp',"$gson;$loader;$mixin",'-d',"$b/classes")+$sources)
Checked "$JavaHome/bin/javac.exe" @('-proc:none','-encoding','UTF-8','-cp',$asm,'-d',"$b/tools","$PSScriptRoot/src/GenerateScreens.java")
Checked "$JavaHome/bin/java.exe" @('-cp',"$b/tools;$asm",'GenerateScreens',"$b/classes")
foreach ($resource in 'fabric.mod.json','akogen.client.mixins.json','names.json','cosmetics.zip') {
    Copy-Item -LiteralPath "$PSScriptRoot/resources/$resource" -Destination "$b/classes/$resource" -Force
}
if($ClassesOnly){Write-Host 'Classes ready; release JAR not packaged';return}
Checked "$JavaHome/bin/jar.exe" @('cf',"$PSScriptRoot/akogen-1.21.11-fabric.jar",'-C',"$b/classes",'.')
Write-Host 'Ready: akogen-1.21.11-fabric.jar'
