#include <cstdio>
#include <cstdlib>
#include <cstring>

int random(int n) {
	return ((rand() << 16 | rand()) & (0x7FFFFFFF)) % n;
}

char const * rstr() {
 	char * ret = new char[21];
	for (int i = 0; i < 20; i++) ret[i] = random(2) + 'a';
	ret[20] = 0;
	return ret;
}

const int N = 123456;
char const * s[N];

int main() {
	for (int i = 0; i < 30000; i++) s[i] = rstr();
	char const * ss = rstr();
	for (int i = 0; i < 30000; i++) printf("put %s %s\n", ss, s[i]);
	for (int i = 0; i < 30000; i++) printf("delete %s %s\n", ss, s[i]);
	for (int i = 0; i < 30000; i++) printf("get %s\n", ss);
return 0;
 	for (int i = 0; i < 100000; i++) {
		int type = random(3);
		if (type == 0) {
			printf("put %s %s\n", ss, rstr());
		} else if (type == 2) {
			printf("get %s\n", rstr());
		} else {
			printf("delete %s %s\n", ss, rstr());
		}
	}
	printf("get %s\n", ss);
}