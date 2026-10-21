# Contexto do Projeto: QuickSort com Hoare (Trabalho AV-2)

Documento de acompanhamento: o que o trabalho pede, as decisões tomadas, o estado atual do código e os próximos passos.

- **Disciplina:** Construção e Análise de Algoritmos (Universidade de Fortaleza)
- **Autor:** João Francisco (sem matrícula no README)

---

## 1. O que o enunciado pede

### Parte I: Implementação (Java)
- QuickSort sobre vetores de inteiros, com **um único procedimento de particionamento** para todas as versões.
- **Versão i:** QuickSort com pivô aleatório.
- **Versão ii:** QuickSort Proporcional, que recebe α e força a divisão α : (1 − α).
- Vetores de entrada: **permutações aleatórias de 1 a n, sem repetição**.
- Tratar o `StackOverflowError` (ex.: recursão na menor partição e laço na maior, ou aumentar a pilha) e documentar a decisão.
- Os **TADs** (tipos abstratos de dados) são especificados pelo aluno e **valem nota**.
- O código precisa **compilar**, estar bem indentado e **comentado**.

### Parte II: Análise de complexidade
| Item | O que analisar | Método |
|---|---|---|
| i | `particionaHoare` | Custo linha a linha, mostrar que é Θ(n) |
| ii | Pivô aleatório (caso médio) | Recorrência → unir somatórios → eliminar somatório → telescopar até T(1) → série harmônica. Discutir se vale para qualquer entrada e se o pior caso ainda pode ocorrer |
| iii | Proporcional (≥ 2 valores de α entre 1/2, 1/3, 1/4, 1/5; **90/10 proibido**) | Árvore de recursão (≥ 3 níveis), h_min, h_max, limites inferior e superior, h_max = k · log₂ n, conclusão para α constante e o caso 0 / n−1 |

Não é preciso analisar os métodos do `Vetor`, o gerador ou o `Main`.

### Documentação
- **A. Introdução:** o problema e uma visão geral do programa.
- **B. Implementação:** estruturas de dados (com diagramas), funções principais, entrada e saída, decisões tomadas.
- **C. Análise de Complexidade:** toda a Parte II, em O/Θ, com **quadro-resumo**.

### Entrega
- Um único `.zip` no AVA (código, arquivos e documentação).
- Documentação impressa da análise no dia da entrega.
- **Apresentação ao professor.** Sem explicação, nota zero.

---

## 2. Decisões tomadas

| Decisão | Motivo |
|---|---|
| Particionamento de **Hoare** | O professor informou pessoalmente que Lomuto não pode ser usado |
| Pivô sempre em `v.get(inicio)` | Usar o último elemento como pivô no Hoare causa recursão infinita (ex.: `[1, 2]` retorna `j = 1` e repete o mesmo intervalo) |
| Recursão em `(inicio, j)` e `(j + 1, fim)` | No Hoare o pivô **não** fica na posição final, então nenhum elemento pode ser descartado |
| Condição de parada do particionamento: `i >= j` | Com `i > j`, o caso `i == j` (pivô é o menor elemento, ex.: `[1, 5, 6]`) leva `j` para −1 |
| Nomes `inicio` e `fim` | Mais didáticos que `lo` e `hi` |
| TAD `Vetor` encapsulando o `int[]` | O enunciado cobra a especificação dos TADs |
| Pivô aleatório = sortear posição e trocar com `inicio` | Assim o `particionaHoare` não muda |
| Embaralhamento de **Fisher-Yates** no gerador | Gera todas as permutações com a mesma probabilidade (ver seção 4) |
| Evitar `Arrays.sort`, `Collections.shuffle`, `Arrays.toString` | O professor não quer bibliotecas que façam o trabalho |
| Valores de α: **1/4** (25/75) e **1/5** (20/80) | Escolha do aluno entre 1/2, 1/3, 1/4 e 1/5 (90/10 proibido) |
| Pivô proporcional escolhido pelo **valor**, não pela posição | Como o vetor é permutação de 1 a n, o valor k tem exatamente k − 1 menores. A posição não serve porque o vetor está embaralhado |
| `buscarPosicao` separado do `quicksortProporcional` | Uma responsabilidade por método: o proporcional decide **qual** valor; a busca descobre **onde** ele está |
| α passado como `1.0 / 4` e `1.0 / 5` | Em Java, `1 / 4` é divisão inteira e vale 0 |
| Sorteio com `Math.random()`, não `java.util.Random` | Faz parte do `java.lang`, sem import. Fica isolado no método `sortear`, então trocar a fonte de aleatoriedade depois mexe em um lugar só |

### Por que Hoare (para a justificativa na documentação)
- Faz cerca de 3 vezes menos trocas que Lomuto, em média.
- Lida bem com elementos repetidos (Lomuto vira O(n²) num vetor todo igual). Neste trabalho os vetores não têm repetição, então essa vantagem não aparece.
- O pior caso **não** depende do particionamento, e sim da escolha do pivô.

---

## 3. Conceitos do Hoare

- `inicio` e `fim` são os **limites fixos** do subarranjo ("as cercas").
- `i` começa em `inicio − 1` e `j` em `fim + 1` ("as pessoas que entram caminhando").
- Cada índice **anda primeiro e depois testa** (por isso o `do-while`):
  - `j` recua até achar um elemento **≤ pivô**;
  - `i` avança até achar um elemento **≥ pivô**.
- Se `i < j`, troca. Se `i >= j`, retorna `j`.
- Garantia final: tudo em `[inicio..j]` é ≤ tudo em `[j+1..fim]`.

Exemplo de referência: `[5, 3, 8, 1, 9, 2, 7]` → `[2, 3, 1 | 8, 9, 5, 7]`, retorna `j = 2`.

---

## 4. Estado atual do código

```
quicksort-hoare/
├── src/
│   ├── Main.java            → testes
│   ├── Vetor.java           → TAD (pronto)
│   ├── GeradorVetores.java  → permutações com Fisher-Yates (pronto)
│   └── Quicksort.java       → Hoare, pivô fixo, aleatório e proporcional (prontos, com tratamento da pilha)
├── docs/
│   ├── documentacao.pdf     → documentação final (A, B, C)
│   ├── documentacao.md      → fonte da documentação
│   └── diagramas/           → árvores de recursão e diagramas
├── specs/
│   └── contexto.md
├── .gitignore
└── README.md
```

### `Vetor.java` (pronto)
Atributo: `private int[] elementos`.
Métodos: `Vetor(int n)`, `tamanho()`, `get(i)`, `set(i, valor)`, `trocar(i, j)`, `imprimir()`, `estaOrdenado()`.

### `GeradorVetores.java` (pronto)
- `criarVetor(n)`: preenche a posição `i` com o valor `i + 1`, gerando `1, 2, ..., n`.
- `sortear(max)`: devolve um inteiro de 0 a `max`, inclusive, com `(int) (Math.random() * (max + 1))`. Os parênteses são obrigatórios: sem eles, o `Math.random()` vira 0 antes da multiplicação.
- `embaralhar(v)`: **Fisher-Yates**. Percorre de `tamanho() - 1` até `i > 0`, sorteia `j` entre 0 e `i` e troca as posições `i` e `j`. A posição 0 não precisa de sorteio: sobra por eliminação.
- `gerarPermutacao(n)`: `criarVetor` + `embaralhar`.

**Por que sortear só entre 0 e `i` (justificativa do Fisher-Yates):**
- Depois que a posição `i` é decidida, ela sai do sorteio e não é mais mexida (como uma carta que já saiu do baralho).
- O primeiro passo tem n opções, o segundo n − 1, ..., totalizando **n! caminhos, um para cada permutação**. Logo, todas as permutações têm a mesma chance.
- Sortear sempre no vetor inteiro gera nⁿ caminhos, que não se dividem igualmente entre as n! permutações (ex.: n = 3 → 27 caminhos para 6 ordens, 27 ÷ 6 = 4,5). O embaralhamento fica viciado.
- Repetir o `j` sorteado **não** é problema: as trocas nunca criam valores repetidos.
- Importância para o trabalho: a análise de caso médio supõe que todas as permutações são igualmente prováveis.

### `Quicksort.java`
- `quicksort(Vetor v, int inicio, int fim)`: pivô fixo no início. Parada em `fim <= inicio`, recursão em `(inicio, j)` e `(j + 1, fim)`.
- `particionaHoare(Vetor v, int inicio, int fim)`: pivô em `v.get(inicio)`, dois `do-while`, `i >= j` retorna `j`, senão `v.trocar(i, j)`. **Usado por todas as versões.**
- `quicksortAleatorio(v, inicio, fim)` (pronto e testado): `posicaoPivo = inicio + GeradorVetores.sortear(fim - inicio)`, troca com `inicio`, particiona, recursão chamando `quicksortAleatorio`.
- `buscarPosicao(v, inicio, fim, valor)`: percorre `inicio..fim` (inclusive) e devolve a posição do valor, ou −1.
- `quicksortProporcional(v, inicio, fim, alpha)` (pronto e testado):
  - `tamanho = fim - inicio + 1` (não `fim`, nem `v.tamanho()`)
  - `menorValor = inicio + 1`
  - `qtdMenores = (int) (tamanho * alpha)`
  - `valorPivo = menorValor + qtdMenores`
  - busca a posição do `valorPivo`, troca com `inicio`, particiona, recursão passando o mesmo `alpha`.
- **Tratamento do `StackOverflowError` (nas três versões):** `while (inicio < fim)`, recursão só no lado menor e atualização de `inicio`/`fim` para o lado maior. Profundidade da pilha ≤ log₂ n. Protege a memória, não melhora o tempo (pior caso continua O(n²)).

**Por que o proporcional funciona (a "regra das cartas"):**
- Pivô = menor carta do monte + quantas cartas devem ficar do lado das menores.
- Cada pedaço `[inicio..fim]` guarda valores em sequência, de `inicio + 1` até `fim + 1`, porque o Hoare manda todos os menores para a esquerda.
- Conferência com α = 1/4: pedaço 0..99 → pivô 26; pedaço 25..99 (tamanho 75, valores 26..100) → 18 menores → pivô 44.
- Arredondar para baixo deixa a proporção inexata em pedaços pequenos; com `qtdMenores = 0`, o pivô é o menor valor e o Hoare separa 1 elemento. Vale uma linha na documentação.
- A busca custa Θ(n) por chamada, igual ao particionamento, então **não muda a complexidade**: o custo por nível continua cn, só com c maior. Usar isso na análise do item iii.

### Casos de teste (todos passam na versão final com `Vetor`)
| Caso | Vetor | Esperado |
|---|---|---|
| exemplo | `{5, 3, 8, 1, 9, 2, 7}` | `1 2 3 5 7 8 9` |
| ordenado | `{1, 2, 3, 4, 5, 6, 7}` | `1 2 3 4 5 6 7` |
| decrescente | `{7, 6, 5, 4, 3, 2, 1}` | `1 2 3 4 5 6 7` |
| um elemento | `{42}` | `42` |
| dois em ordem | `{1, 2}` | `1 2` |
| dois invertidos | `{2, 1}` | `1 2` |
| vazio | `{}` | nada, sem erro |
| pivô menor | `{1, 5, 6}` | `1 5 6` |
| pivô maior | `{9, 4, 7, 2}` | `2 4 7 9` |

Além deles: permutações com n até 1.000.000 nas três versões pedidas e o pior caso do pivô fixo com 20.000 elementos (sem estouro de pilha, mesmo com `-Xss256k`).

---

## 5. Resultados da análise (detalhes em `docs/documentacao.pdf`)

| Item | Resultado |
|---|---|
| i. `particionaHoare` | n ≤ Sᵢ + Sⱼ ≤ n + 2, trocas ≤ n/2 → **Θ(n)** |
| ii. Pivô aleatório (caso médio) | Recorrência do Hoare: T(n) = n + 1/(n−1) · Σ_{k=1}^{n−1} [T(k) + T(n−k)] → T(n) = n·T(1) + 2n(Hₙ − 1) ≈ 1,39 n log₂ n → **Θ(n log n)** |
| iii. α = 1/4 | h_min = log₄ n, h_max = log₄/₃ n ≈ 2,409 log₂ n → **Θ(n log n)** |
| iii. α = 1/5 | h_min = log₅ n, h_max = log₅/₄ n ≈ 3,106 log₂ n → **Θ(n log n)** |
| Partição 0 / n − 1 | **Θ(n²)** |

Observações:
- No Hoare nenhum lado fica vazio: lados k e n − k, com k de 1 a n − 1, e média dividida por n − 1 (diferente da recorrência de sala, que usa Lomuto).
- No Hoare real, a partição 1|n−1 sai com chance 2/n; a diferença para o modelo uniforme é de 1% a 3% e não muda a ordem.
- Os slides 242 a 246 da aula (caso médio) não estavam no PDF enviado; a derivação seguiu a forma clássica adaptada ao Hoare.

---

## 6. Próximos passos

- [x] Código completo, comentado e testado
- [x] Tratamento do `StackOverflowError`
- [x] Análise de complexidade (itens i, ii, iii) e quadro-resumo
- [x] Documentação (A, B, C) com diagramas, em PDF
- [x] `.zip` de entrega
- [ ] Reescrever trechos da documentação com as próprias palavras
- [ ] Imprimir a documentação
- [ ] Ensaiar a apresentação (ver `roteiro-apresentacao.md`) e explicar o trabalho do zero, com a skill de explicação de volta