# Documentação do Aplicativo Android - Coletor de Dados de Produção

## Visão Geral

O aplicativo Android "Coletor de Dados de Produção" é uma ferramenta desenvolvida para coletar informações de produção e tempos ociosos no setor de acabamento. Ele permite que os funcionários registrem dados diretamente em dispositivos Android e os enviem para uma planilha do Google Sheets. O aplicativo é compatível com dispositivos que executam Android 8.1 ou superior e requer uma conta Google para login e envio de dados.

## Funcionalidades

1. **Coleta de Dados de Produção**: Os funcionários podem registrar informações sobre a produção e os tempos parados no setor de acabamento da empresa através do aplicativo.

2. **Armazenamento em Banco de Dados Interno**: Os dados coletados são armazenados em um banco de dados SQL interno ao aplicativo, garantindo a integridade e segurança dos dados.

3. **Envio para Planilha do Google Sheets**: Os usuários podem enviar os dados coletados para uma planilha no Google Sheets com o toque de um botão.

4. **Integração com Google Apps Script**: O envio dos dados para a planilha é realizado através de um projeto do Google Apps Script, onde os dados são formatados em JSON e inseridos na planilha correspondente.

5. **Login via Conta Google**: O acesso ao aplicativo é protegido e requer o login com uma conta Google válida.

6. **Envio de Dados apenas com Conexão**: O usuário só pode enviar os dados coletados quando estiver conectado à internet.

## Requisitos do Sistema

- Dispositivos Android com Android 8.1 (Oreo) ou superior.
- Conexão à internet para enviar dados para o Google Sheets.
- Conta Google válida para login e envio de dados.

## Fluxo de Funcionamento

1. **Login**: O usuário faz login no aplicativo utilizando uma conta Google.

2. **Coleta de Dados**: O funcionário registra os dados de produção e tempos parados no setor de acabamento da empresa.

3. **Armazenamento Local**: Os dados coletados são armazenados localmente no banco de dados SQL do aplicativo.

4. **Envio para Planilha**: Quando o usuário pressiona o botão de envio, os dados são formatados em JSON e enviados para o projeto do Google Apps Script.

5. **Integração com Google Sheets**: O Google Apps Script recebe os dados formatados em JSON e os insere na planilha do Google Sheets correspondente.

6. **Confirmação de Envio**: O usuário recebe uma confirmação de que os dados foram enviados com sucesso.

## Considerações de Segurança

- Todas as comunicações entre o aplicativo e o servidor do Google são realizadas através de conexões seguras.
- Os dados de login do usuário são protegidos e não são armazenados localmente no dispositivo.
- O acesso à planilha do Google Sheets é restrito apenas ao projeto do Google Apps Script.

## Conclusão

O "Coletor de Dados de Produção" é uma solução eficiente para a coleta e registro de informações de produção e tempos parados no setor de acabamento. Com sua integração com o Google Sheets e seu design amigável, proporciona uma maneira fácil e segura de gerenciar e analisar dados de produção em tempo real.
