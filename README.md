# QuickSort com Particionamento de Hoare

Trabalho AV-2 da disciplina Construção e Análise de Algoritmos, Universidade de Fortaleza.

## Sobre o projeto

Implementação do algoritmo QuickSort em Java, sobre vetores de inteiros, usando o particionamento de Hoare. O projeto tem duas versões:

- **QuickSort com pivô aleatório**
- **QuickSort Proporcional**, que recebe um valor α e força a divisão α : (1 − α), executado com α = 1/4 (25/75) e α = 1/5 (20/80)

As duas versões usam o mesmo procedimento de particionamento.

Os vetores de entrada são permutações aleatórias dos inteiros de 1 a n, sem repetição, geradas com o embaralhamento de Fisher-Yates.

A análise de complexidade de cada versão está na documentação, na pasta `docs/`.

## Estrutura

```
quicksort-hoare/
├── src/
│   ├── Main.java            → ponto de entrada e testes
│   ├── Vetor.java           → TAD que encapsula o vetor de inteiros
│   ├── GeradorVetores.java  → gera permutações aleatórias de 1 a n (Fisher-Yates)
│   └── Quicksort.java       → particionamento de Hoare, QuickSort aleatório e proporcional
├── docs/                    → documentação e diagramas
├── specs/                   → anotações de acompanhamento do projeto
├── .gitignore
└── README.md
```

## Requisitos

- JDK 8 ou superior

## Como compilar e executar

Na pasta raiz do projeto:

```bash
javac -d bin src/*.java
java -cp bin Main
```

## Autor

João Francisco