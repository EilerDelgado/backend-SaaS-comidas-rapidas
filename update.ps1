$content = Get-Content plan_de_implementacion_app_de_comida.md -Raw
$newPhase6 = "### Flujo POS y Online`n`n```text`nFlujo Online:`nCliente -> selecciona productos -> crea pedido -> registrado`n`nFlujo POS (Mostrador):`nCajero (ADMIN) -> atiende cliente presencial -> selecciona productos -> ingresa nombre manual -> crea pedido -> registrado`n```"
$content = $content -replace "(?s)### Flujo\r?\n.*?pedido registrado\r?\n```", $newPhase6
Set-Content -Path plan_de_implementacion_app_de_comida.md -Value $content -Encoding UTF8
