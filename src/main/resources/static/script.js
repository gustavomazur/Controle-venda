const API_BASE = 'http://localhost:8080';

function showTab(tabName) {
    const tabs = document.querySelectorAll('.tab-content');
    tabs.forEach(tab => tab.classList.remove('active'));
    document.getElementById(tabName).classList.add('active');
    
    const buttons = document.querySelectorAll('.tab-button');
    buttons.forEach(btn => btn.classList.remove('active'));
    event.target.classList.add('active');
}

document.getElementById('clienteForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const enderecoElements = document.querySelectorAll('#enderecos .endereco');

    const enderecos = [];

    enderecoElements.forEach(div => {
        const endereco = {
            nome: div.querySelector('.nome').value,
            cep: div.querySelector('.cep').value,
            rua: div.querySelector('.rua').value,
            numero: div.querySelector('.numero').value,
            referecncia: div.querySelector('.referencia').value // tá digitado errado no back end, tem q resolver epois
        };

        enderecos.push(endereco);
    });

    const data = {
        nome: document.getElementById('nome').value,
        telefone: document.getElementById('telefone').value,
        endereco: enderecos,
        cpf: document.getElementById('cpf').value,
        tamanho: document.getElementById('tamanho').value,
        foto: document.getElementById('foto').value
    };

    try {
        const response = await fetch(`${API_BASE}/cliente`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert('Cliente cadastrado com sucesso!');
            document.getElementById('clienteForm').reset();
            document.getElementById('enderecos').innerHTML = ''; // limpa os endereços
        } else {
            alert('Erro ao cadastrar cliente.');
        }

    } catch (error) {
        console.error('Error:', error);
        alert('Erro de conexão.');
    }
});

document.getElementById('produtoForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const data = {
        nome: document.getElementById('prodNome').value,
        quantidade: parseInt(document.getElementById('quantidade').value),
        categoria: document.getElementById('categoria').value,
        descricao: document.getElementById('descricao').value,
        precoCompra: parseFloat(document.getElementById('precoCompra').value),
        precoVenda: parseFloat(document.getElementById('precoVenda').value),
        imagem: document.getElementById('imagem').value,
        barraDoProduto: document.getElementById('barraDoProduto').value
    };
    try {
        const response = await fetch(`${API_BASE}/produto`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (response.ok) {
            alert('Produto cadastrado com sucesso!');
            document.getElementById('produtoForm').reset();
        } else {
            alert('Erro ao cadastrar produto.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Erro de conexão.');
    }
});

function adicionarItem() {
    const itensDiv = document.getElementById('itens');
    const itemDiv = document.createElement('div');
    itemDiv.className = 'item';
    itemDiv.innerHTML = `
        <label>Produto ID:</label>
        <input type="number" class="produtoId" required>
        <label>Quantidade:</label>
        <input type="number" class="quantidade" required>
        <label>Preço:</label>
        <input type="number" step="0.01" class="preco" required>
        <button type="button" onclick="removerItem(this)">Remover</button>
    `;
    itensDiv.appendChild(itemDiv);
}

function removerItem(button) {
    button.parentElement.remove();
}

document.getElementById('vendaForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const itens = [];
    document.querySelectorAll('.item').forEach(item => {
        const produtoId = parseInt(item.querySelector('.produtoId').value);
        const quantidade = parseInt(item.querySelector('.quantidade').value);
        const preco = parseFloat(item.querySelector('.preco').value);
        itens.push({ produtoId, quantidade, preco });
    });
    const data = {
        clienteId: parseInt(document.getElementById('clienteId').value),
        clienteNome: document.getElementById('clienteNome').value,
        vendedor: document.getElementById('vendedor').value,
        tipoPagamento: document.getElementById('tipoPagamento').value,
        desconto: {
            tipo: document.getElementById('descontoTipo').value,
            valor: parseFloat(document.getElementById('descontoValor').value) || 0
        },
        itens: itens,
        data: new Date().toISOString()
    };
    try {
        const response = await fetch(`${API_BASE}/venda`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (response.ok) {
            alert('Venda cadastrada com sucesso!');
            document.getElementById('vendaForm').reset();
            document.getElementById('itens').innerHTML = '<h3>Itens da Venda</h3><div class="item"><label>Produto ID:</label><input type="number" class="produtoId" required><label>Quantidade:</label><input type="number" class="quantidade" required><label>Preço:</label><input type="number" step="0.01" class="preco" required><button type="button" onclick="removerItem(this)">Remover</button></div>';
        } else {
            alert('Erro ao cadastrar venda.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Erro de conexão.');
    }
});


function adicionarEndereco(){
    const enderecoDiv = document.getElementById('enderecos');
    const novoEnderecoDiv = document.createElement('div');

    novoEnderecoDiv.className = 'endereco';

    novoEnderecoDiv.innerHTML = `
        <input type="text" placeholder="Nome" class="nome">
        <input type="text" placeholder="CEP" class="cep">
        <input type="text" placeholder="Rua" class="rua">
        <input type="text" placeholder="Número" class="numero">
        <input type="text" placeholder="Referência" class="referencia">
        
        <button type="button" onclick="removerEndereco(this)">Remover</button>
    `;

    enderecoDiv.appendChild(novoEnderecoDiv);
}

function removerEndereco(button){
    button.parentElement.remove();
}
