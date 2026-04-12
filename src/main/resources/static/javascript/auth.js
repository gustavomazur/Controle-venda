const API_URL = '/api/auth';

function mostrarMensagem(formId, texto, tipo) {
    const elemento = document.getElementById(`mensagem-${formId}`);
    if (elemento) {
        elemento.textContent = texto;
        elemento.className = `mensagem ${tipo}`;
        
        setTimeout(() => {
            elemento.className = 'mensagem';
            elemento.textContent = '';
        }, 4000);
    }
}

document.addEventListener('DOMContentLoaded', function() {
    if (estaLogado()) {
        window.location.href = 'index.html';
        return;
    }

    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            fazerLogin();
        });
    }

    const cadastroForm = document.getElementById('cadastro-form');
    if (cadastroForm) {
        cadastroForm.addEventListener('submit', function(e) {
            e.preventDefault();
            fazerCadastro();
        });
    }
});

function fazerLogin() {
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;
    const btn = document.querySelector('#login-form .btn-submit');

    btn.disabled = true;
    btn.textContent = 'Entrando...';

    fetch(`${API_URL}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, senha })
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else if (response.status === 401) {
            throw new Error('E-mail ou senha incorretos');
        } else {
            throw new Error('Erro ao fazer login');
        }
    })
    .then(data => {
        localStorage.setItem('usuario', JSON.stringify(data));
        window.location.href = 'index.html';
    })
    .catch(error => {
        mostrarMensagem('login', error.message, 'error');
        btn.disabled = false;
        btn.textContent = 'Entrar';
    });
}

function fazerCadastro() {
    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;
    const confirmarSenha = document.getElementById('confirmar-senha').value;
    const btn = document.querySelector('#cadastro-form .btn-submit');

    if (senha !== confirmarSenha) {
        mostrarMensagem('cadastro', 'As senhas não coincidem', 'error');
        return;
    }

    btn.disabled = true;
    btn.textContent = 'Cadastrando...';

    fetch(`${API_URL}/cadastro`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ nome, email, senha })
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else if (response.status === 400) {
            return response.json().then(err => {
                throw new Error(err.message || 'Dados inválidos');
            });
        } else {
            throw new Error('Erro ao fazer cadastro');
        }
    })
    .then(data => {
        mostrarMensagem('cadastro', 'Cadastro realizado com sucesso! Redirecionando...', 'success');
        setTimeout(() => {
            window.location.href = 'login.html';
        }, 1500);
    })
    .catch(error => {
        mostrarMensagem('cadastro', error.message, 'error');
        btn.disabled = false;
        btn.textContent = 'Cadastrar';
    });
}

function estaLogado() {
    const usuario = localStorage.getItem('usuario');
    return usuario !== null;
}

function logout() {
    localStorage.removeItem('usuario');
    window.location.href = 'login.html';
}

function getUsuario() {
    const usuario = localStorage.getItem('usuario');
    return usuario ? JSON.parse(usuario) : null;
}
