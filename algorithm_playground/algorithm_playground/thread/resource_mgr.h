#ifndef RES_MGR_H
#define RES_MGR_H

#include <vector>
#include <string>
#include <iostream>
#include <thread>
#include <mutex>
#include <map>

#include <memory>		// shared_ptr, weaked_ptr

#include <stdarg.h>
#include <stdio.h>

using namespace std;

#define MAIN_T
#define DATA_T

enum State
{
	None,
	Loading,
	Loaded,
	Dead
};

// Resource �� ResourceManager ����������չ�ķ�ʽ
//	Resource��չload��ϵ��������Դ���ص�����
//  ResourceManager��Ҫ��չcreateResourceImpl��������Դ�����ķ�ʽ����Ϊ��Դ����ʱͨ���ַ���ָ����
//	�����Դ��Ӵ���Resource*���������޶ȵĴ���Resource�������࣬Ȼ����Resourceָ�����ʽ��ӵ��������С�

void dbg_printf(const char *fmt, ...);

class Resource
{
public:

	Resource() : mSt(None)
	{
		sCounter++;
		mId = sCounter;
		dbg_printf("create resource %d\n", mId);
	}

	~Resource()
	{
		dbg_printf("destroy resource %d\n", mId);
		sCounter--;
	}

	virtual DATA_T bool load() { return true; }

	void SetState(State st) { mSt = st; }
	State getState() { return mSt; }

	int getId() { return mId; }

private:

	static int sCounter;

	State mSt;
	int mId;
};

// ImageMaker

class ImageLoader : public Resource
{
public:

	ImageLoader() : Resource() {}

	bool load();

};

// DrawTile
// DrawText

class ResourceManager
{
public:

	typedef std::shared_ptr<Resource>	share_t;
	typedef std::weak_ptr<Resource>	weak_t;
	typedef std::string str_t;

	ResourceManager();
	~ResourceManager();

	DATA_T void handleTasks();

	MAIN_T share_t addResource(const str_t& key);
	MAIN_T share_t createResource();	
	MAIN_T void deleteResource(share_t& pres);
	
	MAIN_T void renderResource();

	static void smart_test();

protected:

	virtual share_t createResourceImpl() { return share_t(new Resource()); }

private:

	// http://en.cppreference.com/w/cpp/thread
	std::mutex mMutex;

	map< str_t, share_t > resources;

	vector< weak_t > to_load_vec;

};

class ImageManager : public ResourceManager
{
public:

	typedef ResourceManager::share_t share_t;

	static void unit_test();

protected:

	share_t createResourceImpl();

};


#endif