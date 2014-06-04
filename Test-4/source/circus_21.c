#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/stat.h>

#define MAX_FILE_SIZE 10*1024

#define MAX_MAN 100

#define MAX 1000
#define MIN 0

#define OK 0
#define FAIL !OK

#define INPUT_FILE_TMP "/tmp/inputq4.tmp"

typedef struct per_s {
	int height;
	int weight;
} per;

int D;

static int init(const char* input, const char* output) {
	struct stat st;
	freopen(input, "r", stdin);
	freopen(output, "w", stdout);
	/*
	 stat(input, &st);
	 if (st.st_size > MAX_FILE_SIZE) {
	 II();
	 return 1;
	 }
	 */
	return 0;
}

static int cmp(const void *src, const void *dst) {

	per s = *(per *) src;
	per d = *(per *) dst;
	return s.height - d.height;
}

static int lis(per P[], int n) {
	int d[MAX_MAN];
	int len = 1;
	int i, j;
	for (i = 0; i < n; ++i) {
		d[i] = 1;
		for (j = 0; j < i; ++j)
			if (P[j].weight <= P[i].weight && d[j] + 1 > d[i])
				d[i] = d[j] + 1;
		if (d[i] > len)
			len = d[i];
	}

	return len;
}

int stripspace(FILE *fp, char ch) {
#define MAX_SPACE 50
	int i = 0;
	while (!feof(fp) && i < MAX_SPACE) {
		//   	printf("ch=%d[%c]\n", ch, ch);
		if (ch != ' ' && ch != '\t' && ch != '\n') {
			return FAIL;
		} else {
			ch = fgetc(fp);
			i++;
		}
	}
	return OK;
}

int cmpfile(const char *tmp, const char *input) {
	FILE *fp1, *fp2;
	char ch1, ch2;
	int i;

	int res = FAIL;

	fp1 = fopen(tmp, "r");
	fp2 = fopen(input, "r");
	if (!fp1 || !fp2)
		return FAIL;

	do {
		ch1 = fgetc(fp1);
		ch2 = fgetc(fp2);
//		printf("ch1=%d[%c], ch2=%d[%c]\n", ch1, ch1, ch2, ch2);
	} while (ch1 == ch2 && !feof(fp1));

	if (stripspace(fp1, ch1) == OK && stripspace(fp2, ch2) == OK && feof(fp1)
			&& feof(fp2))
		res = OK;

	fclose(fp1);
	fclose(fp2);

	return res;
}

int main(int argc, char* argv[]) {
	int n = 0;
	int i;
	int res;
	per persons[MAX_MAN];
	int max = 0;
	int w, h;
	FILE *fp;
//	if (init("inputq4.txt", "outputq4.txt")) {
	if (init(argv[1], argv[2])) {
		return 1;
	}

	D = 6;

	for (D = 0; scanf("(%d,%d)", &h, &w) == 2; D++) {
		if (h >= 0 && h <= 10000 && w >= 0 && w <= 10000 && D <= MAX_MAN) {
			persons[D].height = h;
			persons[D].weight = w;
		} else {
			printf("Invalid Input\n");
			return 1;
		}
	}

	/*
	 printf("D=%d\n", D);
	 for(i=0;i<D;i++){
	 printf("h=%d, w=%d\n", persons[i].height, persons[i].weight);
	 }
	 */

	unlink(INPUT_FILE_TMP);
	fp = fopen(INPUT_FILE_TMP, "w");
	for (i = 0; i < D; i++) {
		fprintf(fp, "(%d,%d)", persons[i].height, persons[i].weight);
	}
	fclose(fp);

	if (cmpfile(INPUT_FILE_TMP, argv[1]) != OK) {
		printf("Invalid Input\n");
		unlink(INPUT_FILE_TMP);
		return 1;
	}

	unlink(INPUT_FILE_TMP);
	qsort(persons, D, sizeof(per), cmp);

	/*
	 for(i=0;i<D;i++){
	 printf("h=%d, w=%d\n", persons[i].height, persons[i].weight);
	 }
	 */

	res = lis(persons, D);

	printf("%d", res);

	return 0;
}
