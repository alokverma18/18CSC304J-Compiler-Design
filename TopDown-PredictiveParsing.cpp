#include <iostream>
#include <vector>
#include <map>
#include <set>

using namespace std;

class Grammar {
private:
    map<char, vector<string>> productions;
    set<char> nonTerminals;
    set<char> terminals;

public:
    Grammar(map<char, vector<string>> prod) {
        productions = prod;
        for (auto const &entry : prod) {
            nonTerminals.insert(entry.first);
            for (auto const &prod : entry.second) {
                for (char symbol : prod) {
                    if (!isupper(symbol) && symbol != '|') {
                        terminals.insert(symbol);
                    }
                }
            }
        }
    }

    void eliminateLeftRecursion() {
        map<char, vector<string>> newProductions;

        for (char nonTerminal : nonTerminals) {
            vector<string> alpha, beta;
            for (string prod : productions[nonTerminal]) {
                if (prod[0] == nonTerminal) {
                    alpha.push_back(prod.substr(1));
                } else {
                    beta.push_back(prod);
                }
            }

            if (!alpha.empty()) {
                char newNonTerminal = nonTerminal + 1;
                newProductions[newNonTerminal] = alpha;
                for (string &prod : beta) {
                    prod += newNonTerminal;
                }
                newProductions[nonTerminal] = beta;
            } else {
                newProductions[nonTerminal] = productions[nonTerminal];
            }
        }

        productions = newProductions;
    }

    void eliminateLeftFactoring() {
        map<char, vector<string>> newProductions;

        for (char nonTerminal : nonTerminals) {
            map<char, vector<string>> commonPrefixes;
            for (string prod : productions[nonTerminal]) {
                char prefix = prod[0];
                if (commonPrefixes.find(prefix) != commonPrefixes.end()) {
                    commonPrefixes[prefix].push_back(prod.substr(1));
                } else {
                    commonPrefixes[prefix] = {prod.substr(1)};
                }
            }

            for (auto const &entry : commonPrefixes) {
                if (entry.second.size() > 1) {
                    char newNonTerminal = nonTerminal + 1;
                    newProductions[nonTerminal].push_back(string(1, entry.first) + newNonTerminal);
                    newProductions[newNonTerminal] = entry.second;
                } else {
                    newProductions[nonTerminal].push_back(string(1, entry.first) + entry.second[0]);
                }
            }
        }

        productions = newProductions;
    }

    map<char, set<char>> constructFirst() {
        map<char, set<char>> first;

        for (char nonTerminal : nonTerminals) {
            first[nonTerminal] = {};
        }

        bool updated = true;
        while (updated) {
            updated = false;
            for (auto const &entry : productions) {
                char nonTerminal = entry.first;
                for (string prod : entry.second) {
                    char symbol = prod[0];
                    if (!isupper(symbol) || symbol == '|') {
                        if (symbol != '#' && first[nonTerminal].find(symbol) == first[nonTerminal].end()) {
                            first[nonTerminal].insert(symbol);
                            updated = true;
                        }
                    } else {
                        bool allHaveEpsilon = true;
                        for (char s : prod) {
                            if (s != nonTerminal && first[s].find('#') == first[s].end()) {
                                allHaveEpsilon = false;
                                if (first[nonTerminal].find(s) == first[nonTerminal].end()) {
                                    first[nonTerminal].insert(s);
                                    updated = true;
                                }
                                break;
                            }
                        }
                        if (allHaveEpsilon && first[nonTerminal].find('#') == first[nonTerminal].end()) {
                            first[nonTerminal].insert('#');
                            updated = true;
                        }
                    }
                }
            }
        }

        return first;
    }

    map<char, set<char>> constructFollow(map<char, set<char>> first) {
        map<char, set<char>> follow;
        for (char nonTerminal : nonTerminals) {
            follow[nonTerminal] = {};
        }
        

        bool updated = true;
        while (updated) {
            updated = false;
            for (auto const &entry : productions) {
                char nonTerminal = entry.first;
                for (string prod : entry.second) {
                    for (size_t i = 0; i < prod.size(); ++i) {
                        char symbol = prod[i];
                        if (isupper(symbol) && symbol != '|') {
                            if (i + 1 < prod.size() && !isupper(prod[i + 1]) && prod[i + 1] != '|') {
                                if (follow[symbol].find(prod[i + 1]) == follow[symbol].end()) {
                                    follow[symbol].insert(prod[i + 1]);
                                    updated = true;
                                }
                            } else {
                                bool allHaveEpsilon = true;
                                for (size_t j = i + 1; j < prod.size(); ++j) {
                                    char s = prod[j];
                                    if (!isupper(s) || s == '|') {
                                        if (follow[symbol].find(s) == follow[symbol].end()) {
                                            follow[symbol].insert(s);
                                            updated = true;
                                        }
                                        allHaveEpsilon = false;
                                        break;
                                    } else {
                                        for (char f : first[s]) {
                                            if (f != '#' && follow[symbol].find(f) == follow[symbol].end()) {
                                                follow[symbol].insert(f);
                                                updated = true;
                                            }
                                        }
                                        if (first[s].find('#') == first[s].end()) {
                                            allHaveEpsilon = false;
                                            break;
                                        }
                                    }
                                }
                                if (allHaveEpsilon && follow[symbol].find('#') == follow[symbol].end()) {
                                    for (char f : follow[nonTerminal]) {
                                        if (follow[symbol].find(f) == follow[symbol].end()) {
                                            follow[symbol].insert(f);
                                            updated = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return follow;
    }

    void displayProductions() {
        for (auto const &entry : productions) {
            cout << entry.first << " -> ";
            for (string prod : entry.second) {
                cout << prod << " | ";
            }
            cout << endl;
        }
    }

    void displayFirst(map<char, set<char>> first) {
        for (auto const &entry : first) {
            cout << "First(" << entry.first << ") = { ";
            for (char f : entry.second) {
                cout << f << " ";
            }
            cout << "}" << endl;
        }
    }

    void displayFollow(map<char, set<char>> follow) {
        for (auto const &entry : follow
        ) {
            cout << "Follow(" << entry.first << ") = { ";
            for (char f : entry.second) {
                cout << f << " ";
            }
            cout << "}" << endl;
        }
    }
};

int main() {
    map<char, vector<string>> productions = {
        {'E', {"E+T", "T"}},
        {'T', {"T*F", "F"}},
        {'F', {"(E)", "id"}}
    };

    Grammar grammar(productions);

    cout << "Original Productions:" << endl;
    grammar.displayProductions();

    grammar.eliminateLeftRecursion();
    cout << "\nProductions after left recursion elimination:" << endl;
    grammar.displayProductions();

    grammar.eliminateLeftFactoring();
    cout << "\nProductions after left factoring:" << endl;
    grammar.displayProductions();

    auto first = grammar.constructFirst();
    cout << "\nFirst sets:" << endl;
    grammar.displayFirst(first);

    auto follow = grammar.constructFollow(first);
    cout << "\nFollow sets:" << endl;
    grammar.displayFollow(follow);

    return 0;
}
