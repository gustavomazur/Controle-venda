# 📋 Documentação - Formulário de Cadastro de Cliente Atualizado

## ✅ Alterações Realizadas

### 1. **HTML (index.html)**
O formulário de cadastro de cliente agora inclui:

#### Campos Básicos:
- ✅ Nome
- ✅ CPF
- ✅ Telefone
- ✅ Tamanho
- ✅ Foto (upload de arquivo)

#### Nova Seção: Endereços
- ✅ Botão "+ Adicionar Endereço"
- ✅ Modal para adicionar endereços com os campos:
  - Nome do Endereço (ex: Casa, Trabalho)
  - CEP
  - Rua
  - Número
  - Referência

### 2. **CSS (style.css)**
Adicionado:
- ✅ Estilos para seção de endereços
- ✅ Estilos para cards de endereço
- ✅ Modal com animações (slideIn)
- ✅ Botões de ação (adicionar, remover)
- ✅ Responsividade

### 3. **JavaScript (script.js)**
Implementado:
- ✅ Gerenciamento de endereços (adicionar, remover, visualizar)
- ✅ Modal de endereços (abrir, fechar, validar)
- ✅ Upload de arquivo de foto (FormData)
- ✅ Validação de formulário (foto e endereços obrigatórios)
- ✅ Armazenamento temporário de endereços

---

## 🔄 Fluxo de Cadastro

1. Usuário preenche: **Nome**, **CPF**, **Telefone**, **Tamanho**, **Foto**
2. Clica no botão **"+ Adicionar Endereço"**
3. Modal abre com campos de endereço
4. Preenche: **Nome do Endereço**, **CEP**, **Rua**, **Número**, **Referência**
5. Clica em **"Adicionar"**
6. Endereço aparece como card abaixo
7. Pode adicionar múltiplos endereços
8. Clica em **"Cadastrar Cliente"** para enviar tudo

---

## 📦 Estrutura de Dados Enviados (quando API ativada)

```json
{
  "nome": "João Silva",
  "cpf": "123.456.789-00",
  "telefone": "(11) 99999-9999",
  "tamanho": "M",
  "foto": <arquivo>,
  "endereco": [
    {
      "nome": "Casa",
      "cpe": "01234-567",
      "rua": "Rua das Flores",
      "numero": "123",
      "referencia": "Próximo ao parque"
    },
    {
      "nome": "Trabalho",
      "cpe": "01235-678",
      "rua": "Avenida Paulista",
      "numero": "1000",
      "referencia": "Sala 500"
    }
  ]
}
```

---

## 🎨 Visual

### Antes (Simples):
- Nome, Email, Telefone, Endereço (texto único)

### Depois (Completo):
- Nome, CPF, Telefone, Tamanho, Foto (arquivo)
- Seção expandível de Endereços com:
  - Botão "Adicionar Endereço"
  - Cards visuais dos endereços adicionados
  - Botão de remover endereço (X vermelho)
  - Modal elegante para adicionar novo endereço

---

## 🔗 Próximos Passos (Quando Ativar API)

No arquivo `script.js`, procure por:
```javascript
// Comentado: Ativar para consumir a API
/*
fetch('/api/clientes', {
    method: 'POST',
    body: formData
})
```

Descomente este código e ajuste conforme sua API retornar os dados.

---

## ✨ Observações Importantes

1. **FormData**: Usado para enviar arquivo de foto junto com dados JSON
2. **Array de Endereços**: Os endereços são armazenados temporariamente em `enderecosTemporarios` e enviados como JSON
3. **Validação**: O formulário garante que pelo menos um endereço seja adicionado
4. **Responsividade**: Modal e cards se adaptam a telas menores


