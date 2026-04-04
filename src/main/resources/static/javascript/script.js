/* ============================
   GERENCIAR NAVEGAÇÃO E ESTADO
   ============================ */
document.addEventListener('DOMContentLoaded', function () {
    inicializarNavegacao();
    carregarDadosIniciais();
    inicializarModalEnderecos();
});

// Função para inicializar a navegação
function inicializarNavegacao() {
    const navItems = document.querySelectorAll('.nav-item');

    navItems.forEach(item => {
        item.addEventListener('click', function (e) {
            e.preventDefault();

            // Obter o ID da página a ser exibida
            const pageId = this.getAttribute('data-page');

            // Remover classe 'active' de todos os itens
            navItems.forEach(nav => nav.classList.remove('active'));

            // Adicionar classe 'active' ao item clicado
            this.classList.add('active');

            // Trocar página
            trocarPagina(pageId);
        });
    });
}

// Função para trocar a página exibida
function trocarPagina(pageId) {
    // Ocultar todas as páginas
    const pages = document.querySelectorAll('.page');
    pages.forEach(page => page.classList.remove('active'));

    // Exibir a página selecionada
    const pageAtiva = document.getElementById(pageId);
    if (pageAtiva) {
        pageAtiva.classList.add('active');
    }
}

/* ============================
   CARREGAR DADOS INICIAIS
   ============================ */
function carregarDadosIniciais() {
    // Carregar dados da API
    carregarTotalClientes();
    carregarTotalProdutos();
    carregarTotalVendas();
    carregarClientesSelect();
    carregarProdutosSelect();
}

/* ============================
   CONSUMO DE API - DASHBOARD
   ============================ */

// Carregar total de clientes
function carregarTotalClientes() {
    fetch('/api/clientes')
        .then(response => response.json())
        .then(data => {
            document.getElementById('total-clientes').textContent = data.length;
        })
        .catch(error => {
            console.error('Erro ao carregar clientes:', error);
            document.getElementById('total-clientes').textContent = '0';
        });
}

// Carregar total de produtos
function carregarTotalProdutos() {
    fetch('/api/produtos')
        .then(response => response.json())
        .then(data => {
            document.getElementById('total-produtos').textContent = data.length;
        })
        .catch(error => {
            console.error('Erro ao carregar produtos:', error);
            document.getElementById('total-produtos').textContent = '0';
        });
}

// Carregar total de vendas
function carregarTotalVendas() {
    fetch('/api/vendas')
        .then(response => response.json())
        .then(data => {
            document.getElementById('total-vendas').textContent = data.length;
        })
        .catch(error => {
            console.error('Erro ao carregar vendas:', error);
            document.getElementById('total-vendas').textContent = '0';
        });
}

/* ============================
   CONSUMO DE API - FORMULÁRIOS
   ============================ */

// Função para carregar clientes no select
function carregarClientesSelect() {
    fetch('/api/clientes')
        .then(response => response.json())
        .then(data => {
            const select = document.getElementById('venda-cliente');
            data.forEach(cliente => {
                const option = document.createElement('option');
                option.value = cliente.id;
                option.textContent = cliente.nome;
                select.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Erro ao carregar clientes:', error);
        });
}

// Função para carregar produtos no select
function carregarProdutosSelect() {
    fetch('/api/produtos')
        .then(response => response.json())
        .then(data => {
            const select = document.getElementById('venda-produto');
            data.forEach(produto => {
                const option = document.createElement('option');
                option.value = produto.id;
                option.textContent = produto.nome;
                select.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Erro ao carregar produtos:', error);
        });
}

/* ============================
   GERENCIAR ENDEREÇOS (MODAL)
   ============================ */

// Array para armazenar endereços temporariamente
let enderecosTemporarios = [];

function inicializarModalEnderecos() {
    const modal = document.getElementById('modal-endereco');
    const btnAbrirModal = document.getElementById('btn-adicionar-endereco');
    const btnFecharModal = document.getElementById('btn-cancel-endereco');
    const closeSpan = document.querySelector('.modal-close');
    const formEndereco = document.getElementById('form-endereco');

    // Abrir modal
    btnAbrirModal.addEventListener('click', function (e) {
        e.preventDefault();
        modal.classList.add('active');
    });

    // Fechar modal (botão cancelar)
    btnFecharModal.addEventListener('click', function (e) {
        e.preventDefault();
        modal.classList.remove('active');
        formEndereco.reset();
    });

    // Fechar modal (X)
    closeSpan.addEventListener('click', function () {
        modal.classList.remove('active');
        formEndereco.reset();
    });

    // Fechar modal ao clicar fora
    window.addEventListener('click', function (event) {
        if (event.target === modal) {
            modal.classList.remove('active');
            formEndereco.reset();
        }
    });

    // Enviar formulário de endereço
    formEndereco.addEventListener('submit', function (e) {
        e.preventDefault();

        const endereco = {
            nome: document.getElementById('endereco-nome').value,
            cpe: document.getElementById('endereco-cpe').value,
            rua: document.getElementById('endereco-rua').value,
            numero: document.getElementById('endereco-numero').value,
            referencia: document.getElementById('endereco-referencia').value
        };

        // Adicionar à lista temporária
        enderecosTemporarios.push(endereco);

        // Atualizar visualização
        atualizarListaEnderecos();

        // Fechar modal e resetar form
        modal.classList.remove('active');
        formEndereco.reset();
    });
}

function atualizarListaEnderecos() {
    const listaEnderecos = document.getElementById('lista-enderecos');
    listaEnderecos.innerHTML = '';

    enderecosTemporarios.forEach((endereco, index) => {
        const card = document.createElement('div');
        card.className = 'endereco-card';
        card.innerHTML = `
            <button type="button" class="endereco-remove" onclick="removerEndereco(${index})">×</button>
            <h4>${endereco.nome}</h4>
            <p><strong>CEP:</strong> ${endereco.cpe}</p>
            <p><strong>Rua:</strong> ${endereco.rua}, ${endereco.numero}</p>
            ${endereco.referencia ? `<p><strong>Referência:</strong> ${endereco.referencia}</p>` : ''}
        `;
        listaEnderecos.appendChild(card);
    });
}

function removerEndereco(index) {
    enderecosTemporarios.splice(index, 1);
    atualizarListaEnderecos();
}

/* ============================
   FORMULÁRIO DE CLIENTE
   ============================ */
document.getElementById('form-cliente')?.addEventListener('submit', function (e) {
    e.preventDefault();

    // Obter dados do formulário
    const nome = document.getElementById('cliente-nome').value;
    const cpf = document.getElementById('cliente-cpf').value;
    const telefone = document.getElementById('cliente-telefone').value;
    const tamanho = document.getElementById('cliente-tamanho').value;
    const fotoInput = document.getElementById('cliente-foto');
    const foto = fotoInput.files[0];

    // Validar se tem foto
    if (!foto) {
        mostrarMensagem('cliente', 'Por favor, selecione uma foto!', 'error');
        return;
    }

    // Validar se tem endereço
    if (enderecosTemporarios.length === 0) {
        mostrarMensagem('cliente', 'Por favor, adicione pelo menos um endereço!', 'error');
        return;
    }

    // Criar FormData para enviar arquivo e dados
    const formData = new FormData();
    formData.append('nome', nome);
    formData.append('cpf', cpf);
    formData.append('telefone', telefone);
    formData.append('tamanho', tamanho);
    formData.append('foto', foto);
    formData.append('endereco', JSON.stringify(enderecosTemporarios));

    // Enviar para API
    fetch('/api/clientes', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else if (response.status === 400) {
            return response.json().then(err => {
                throw new Error(err.message || 'Dados inválidos');
            });
        } else {
            throw new Error('Erro ao cadastrar cliente');
        }
    })
    .then(data => {
        console.log('Cliente cadastrado:', data);
        mostrarMensagem('cliente', 'Cliente cadastrado com sucesso!', 'success');
        document.getElementById('form-cliente').reset();
        enderecosTemporarios = [];
        atualizarListaEnderecos();
        // Recarregar dados do dashboard
        setTimeout(() => carregarTotalClientes(), 1000);
    })
    .catch(error => {
        console.error('Erro:', error);
        mostrarMensagem('cliente', error.message || 'Erro ao conectar com o servidor.', 'error');
    });
});

/* ============================
   FORMULÁRIO DE PRODUTO
   ============================ */
document.getElementById('form-produto')?.addEventListener('submit', function (e) {
    e.preventDefault();

    const nome = document.getElementById('produto-nome').value;
    const descricao = document.getElementById('produto-descricao').value;
    const preco = document.getElementById('produto-preco').value;
    const quantidade = document.getElementById('produto-quantidade').value;
    const foto = document.getElementById('produto-foto').files[0];

    // Criar FormData para enviar arquivo
    const formData = new FormData();
    formData.append('nome', nome);
    formData.append('descricao', descricao);
    formData.append('preco', preco);
    formData.append('quantidade', quantidade);
    if (foto) {
        formData.append('foto', foto);
    }

    // Enviar para API
    fetch('/api/produtos', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Erro ao cadastrar produto');
        }
    })
    .then(data => {
        mostrarMensagem('produto', 'Produto cadastrado com sucesso!', 'success');
        document.getElementById('form-produto').reset();
        // Recarregar selects e dashboard
        carregarProdutosSelect();
        carregarTotalProdutos();
    })
    .catch(error => {
        console.error('Erro:', error);
        mostrarMensagem('produto', 'Erro ao conectar com o servidor.', 'error');
    });
});

/* ============================
   FORMULÁRIO DE VENDA
   ============================ */
document.getElementById('form-venda')?.addEventListener('submit', function (e) {
    e.preventDefault();

    const clienteId = document.getElementById('venda-cliente').value;
    const produtoId = document.getElementById('venda-produto').value;
    const quantidade = document.getElementById('venda-quantidade').value;
    const desconto = document.getElementById('venda-desconto').value || 0;

    if (!clienteId || !produtoId) {
        mostrarMensagem('venda', 'Por favor, selecione um cliente e um produto!', 'error');
        return;
    }

    const vendaData = {
        clienteId: clienteId,
        produtoId: produtoId,
        quantidade: quantidade,
        desconto: desconto
    };

    // Enviar para API
    fetch('/api/vendas', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(vendaData)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Erro ao registrar venda');
        }
    })
    .then(data => {
        mostrarMensagem('venda', 'Venda registrada com sucesso!', 'success');
        document.getElementById('form-venda').reset();
        // Recarregar dashboard
        carregarTotalVendas();
    })
    .catch(error => {
        console.error('Erro:', error);
        mostrarMensagem('venda', 'Erro ao conectar com o servidor.', 'error');
    });
});

/* ============================
   FUNÇÕES AUXILIARES
   ============================ */

// Função para exibir mensagens
function mostrarMensagem(tipo, texto, classe) {
    const elemento = document.getElementById(`mensagem-${tipo}`);
    if (elemento) {
        elemento.textContent = texto;
        elemento.className = `mensagem ${classe}`;

        // Remover mensagem após 4 segundos
        setTimeout(() => {
            elemento.className = 'mensagem';
            elemento.textContent = '';
        }, 4000);
    }
}

// Função para formatar moeda
function formatarMoeda(valor) {
    return new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL'
    }).format(valor);
}

// Função para formatar data
function formatarData(data) {
    return new Intl.DateTimeFormat('pt-BR').format(new Date(data));
}

// Adiciona funções para carregar e exibir listas de clientes e produtos

// Função para exibir lista de clientes
function exibirListaClientes() {
    fetch('/api/clientes')
        .then(response => response.json())
        .then(clientes => {
            const tabela = document.createElement('table');
            tabela.className = 'tabela';
            tabela.innerHTML = `
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>CPF</th>
                        <th>Telefone</th>
                        <th>Tamanho</th>
                        <th>Foto</th>
                    </tr>
                </thead>
                <tbody>
                    ${clientes.map(cliente => `
                        <tr>
                            <td>${cliente.id}</td>
                            <td>${cliente.nome}</td>
                            <td>${cliente.cpf}</td>
                            <td>${cliente.telefone}</td>
                            <td>${cliente.tamanho}</td>
                            <td>${cliente.foto ? `<img src="${cliente.foto}" alt="Foto" style="width:40px;height:40px;border-radius:50%">` : ''}</td>
                        </tr>
                    `).join('')}
                </tbody>
            `;
            document.getElementById('tabela-clientes').innerHTML = '';
            document.getElementById('tabela-clientes').appendChild(tabela);
        })
        .catch(() => {
            document.getElementById('tabela-clientes').innerHTML = '<p>Erro ao carregar clientes.</p>';
        });
}

// Função para exibir lista de produtos
function exibirListaProdutos() {
    fetch('/api/produtos')
        .then(response => response.json())
        .then(produtos => {
            const tabela = document.createElement('table');
            tabela.className = 'tabela';
            tabela.innerHTML = `
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Descrição</th>
                        <th>Preço</th>
                        <th>Quantidade</th>
                        <th>Foto</th>
                    </tr>
                </thead>
                <tbody>
                    ${produtos.map(produto => `
                        <tr>
                            <td>${produto.id}</td>
                            <td>${produto.nome}</td>
                            <td>${produto.descricao || ''}</td>
                            <td>${produto.preco ? formatarMoeda(produto.preco) : ''}</td>
                            <td>${produto.quantidade || ''}</td>
                            <td>${produto.foto ? `<img src="${produto.foto}" alt="Foto" style="width:40px;height:40px;border-radius:50%">` : ''}</td>
                        </tr>
                    `).join('')}
                </tbody>
            `;
            document.getElementById('tabela-produtos').innerHTML = '';
            document.getElementById('tabela-produtos').appendChild(tabela);
        })
        .catch(() => {
            document.getElementById('tabela-produtos').innerHTML = '<p>Erro ao carregar produtos.</p>';
        });
}

// Hook para navegação SPA
const navItems = document.querySelectorAll('.nav-item');
navItems.forEach(item => {
    item.addEventListener('click', function () {
        const pageId = this.getAttribute('data-page');
        if (pageId === 'listar-clientes') {
            exibirListaClientes();
        }
        if (pageId === 'listar-produtos') {
            exibirListaProdutos();
        }
    });
});
