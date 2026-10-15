# QuickSort com Particionamento de Hoare

Trabalho AV-2 da disciplina Construção de Algoritmos, Universidade de Fortaleza.

## Sobre o projeto

Implementação do algoritmo QuickSort em Java, sobre vetores de inteiros, usando o particionamento de Hoare. O projeto tem duas versões:

- **QuickSort com pivô aleatório**
- **QuickSort Proporcional**, que recebe um valor α e força a divisão α : (1 − α)

Os vetores de entrada são permutações aleatórias dos inteiros de 1 a n, sem repetição.

A análise de complexidade de cada versão está na documentação, na pasta `docs/`.

## Estrutura

```
quicksort-hoare/
├── src/
│   ├── Main.java
│   ├── Vetor.java
│   ├── GeradorVetores.java
│   └── QuickSort.java
├── docs/
│   ├── documentacao.pdf
│   └── diagramas/
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

João Francisco Morais Paz 
