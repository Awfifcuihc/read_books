#include "http_test.h"

int main()
{
	const char* city = "http://snsrtts.map.qq.com/?qt=rtt&c=������";
	const char* china = "http://snsrtts.map.qq.com/?qt=rtt&c=ȫ��";

	HttpTest app;
	app.start_download(city);
	app.start_download(china);

	printf("any key pressed to exit...\n");
	getchar();

	// add request
	// process results.

	return 0;
}