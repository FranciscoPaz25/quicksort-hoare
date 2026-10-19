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
│   └── Quicksort.java       → Hoare + recursão (pronto, pivô fixo)
├── docs/
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

### `Quicksort.java` (pronto, pivô fixo no início)
- `quicksort(Vetor v, int inicio, int fim)`: parada em `fim <= inicio`, recursão em `(inicio, j)` e `(j + 1, fim)`.
- `particionaHoare(Vetor v, int inicio, int fim)`: pivô em `v.get(inicio)`, dois `do-while`, `i >= j` retorna `j`, senão `v.trocar(i, j)`.
- Opcional: método de entrada `quicksort(Vetor v)` por sobrecarga, chamando a versão completa com o vetor inteiro.

### Casos de teste (todos passaram com a versão `int[]`)
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

Rodar de novo após a migração para o `Vetor`.

---

## 5. Plano da análise de complexidade

1. **Item i:** tabela linha | custo | vezes. Nos `do-while`, contar os passos **no total da execução**: `i` e `j` juntos percorrem o vetor uma vez (mais 1 ou 2 passos ao se cruzarem). Trocas ≤ n/2. Resultado: Θ(n).
2. **Item ii:** montar a recorrência a partir do código. Atenção: a recorrência de sala provavelmente usa Lomuto (subproblemas q − 1 e n − q). No Hoare, os subproblemas são `[inicio..j]` e `[j+1..fim]`, sem ninguém sair. Saber explicar a diferença.
3. **Item iii:** recorrência → árvore → h_min e h_max → cn · h_min ≤ T(n) ≤ cn · h_max → complexidade.

Fazer a análise **depois** do código final, para evitar retrabalho.

---

## 6. Próximos passos

- [x] `GeradorVetores.gerarPermutacao(n)`: preencher 1..n e embaralhar com Fisher-Yates
- [x] Testar o gerador no `Main` (ordens diferentes a cada execução, 1..n sem repetir) e conferir com `estaOrdenado()` após o QuickSort
- [x] Adaptar o `Main` para os testes com `Vetor`
- [ ] Pivô aleatório: sortear posição entre `inicio` e `fim` (o `sortear` vai de 0 a `max`, então precisa de ajuste) e trocar com `inicio`
- [ ] QuickSort Proporcional com α
- [ ] Tratamento do `StackOverflowError`
- [ ] Comentários no código
- [ ] Testar compilação pelo terminal (`javac -d bin src/*.java`)
- [ ] Análise de complexidade (itens i, ii, iii) e quadro-resumo
- [ ] Documentação (A, B, C) com diagramas
- [ ] Ensaiar a apresentação