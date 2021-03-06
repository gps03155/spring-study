package com.douzone.springcontainer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.douzone.springcontainer.soundsystem.CDPlayer;
import com.douzone.springcontainer.soundsystem.CompactDisc;
import com.douzone.springcontainer.user.User;
import com.douzone.springcontainer.videosystem.BlankDisc;
import com.douzone.springcontainer.videosystem.DVDPlayer;
import com.douzone.springcontainer.videosystem.DigitalVideoDisc;

import config.mixing.videosystem.DVDPlayerConfig;
import config.mixing.videosystem.VideoSystemConfig01;
import config.mixing.videosystem.VideoSystemConfig02;
import config.mixing.videosystem.VideoSystemConfig03;
import config.user.AppConfig01;

public class JavaConfigTest {

	public static void main(String[] args) {
		// testJavaConfigTest01();
		// testJavaConfigTest02();
		// testJavaConfigTest03();
		// testJavaConfigTest04();
		// testJavaConfigTest05();
		// testJavaConfigTest06();
		// testJavaConfigTest07();
		testJavaConfigTest08();
	}

	// Java Config 01
	// 직접 설정 자바 클래스를 전달하는 경우
	// 설정 자바 클래스에 @Configuration 필요 없음 - 보통은 붙임
	public static void testJavaConfigTest01() {
		ApplicationContext appCtx = new AnnotationConfigApplicationContext(AppConfig01.class);

		User user = appCtx.getBean(User.class);
		System.out.println(user);

		((ConfigurableApplicationContext) appCtx).close();
	}

	// Java Config 02
	// 설정 자바 클래스가 있는 베이스 패키지를 지정하는 방법
	// 설정 자바 클래스에 @Configuration 반드시 필요
	public static void testJavaConfigTest02() {
		ApplicationContext appCtx = new AnnotationConfigApplicationContext("config.user");

		User user = appCtx.getBean(User.class);
		System.out.println(user);
	}

	// Java Config 03 - 자동 설정
	// Component Scanning (@Component - bean 클래스, @Autowired - 주입)
	public static void testJavaConfigTest03() {
		// 해당 패키지에서 @Configuration 어노테이션을 가지는 클래스 찾음
		ApplicationContext appCtx = new AnnotationConfigApplicationContext("config.soundsystem");

		CompactDisc cd = appCtx.getBean(CompactDisc.class);
		System.out.println(cd);

		cd = (CompactDisc) appCtx.getBean("blueBlood"); // id로 찾기
		System.out.println(cd);

		CDPlayer cdPlayer = appCtx.getBean(CDPlayer.class);
		cdPlayer.play();

		((ConfigurableApplicationContext) appCtx).close();
	}

	// Java Config 04 - 명시적 설정
	// ComponentScan을 사용하지 않음
	// Java Config Class의 메서드와 @Bean을 사용
	public static void testJavaConfigTest04() {
		// Java Config 할때 사용 - package로 scanning
		ApplicationContext appCtx = new AnnotationConfigApplicationContext("config.videosystem");

		DigitalVideoDisc dvd = appCtx.getBean(DigitalVideoDisc.class); // interface로 구현한 DigitalVideoDisc 클래스
		System.out.println(dvd); // Config 클래스에 @Bean 달아줘야함

		DVDPlayer dvdPlayer = appCtx.getBean(DVDPlayer.class);
		dvdPlayer.play();

		// DVDPlayer dvdPlayer = (DVDPlayer) appCtx.getBean("dvdPlayer2");
		// dvdPlayer.play();

		((ConfigurableApplicationContext) appCtx).close();
	}

	// Java Config 05 - mixing 01
	// Java Config <- Java Config  (Java Config를 2개로 분리)
	public static void testJavaConfigTest05() {
		ApplicationContext appCtx = new AnnotationConfigApplicationContext(DVDPlayerConfig.class); // class로 명시
		
		DVDPlayer dvdPlayer = appCtx.getBean(DVDPlayer.class);
		dvdPlayer.play();
		
		((ConfigurableApplicationContext) appCtx).close();
	}

	// Java Config 06 - mixing 02
	// Java Config <- Java Config + Java Config (Java Config 2개를 나눠서 새로운 Java Config를 만듦 - 전체를 아우를 수 있도록)
	public static void testJavaConfigTest06() {
		ApplicationContext appCtx = new AnnotationConfigApplicationContext(VideoSystemConfig01.class);
		
		DVDPlayer dvdPlayer = appCtx.getBean(DVDPlayer.class);
		dvdPlayer.play();
		
		((ConfigurableApplicationContext) appCtx).close();
	}
	
	// Java Config 07 - mixing 03
	// Java Config <- Java Config + XML Config
	public static void testJavaConfigTest07() {
		ApplicationContext appCtx = new AnnotationConfigApplicationContext(VideoSystemConfig02.class);
		
		DVDPlayer dvdPlayer = appCtx.getBean(DVDPlayer.class);
		dvdPlayer.play();
		
		((ConfigurableApplicationContext) appCtx).close();
	}
	
	// Java Config 08 - mixing 04
	// Java Config <- XML Config
	public static void testJavaConfigTest08() {
		ApplicationContext appCtx = new AnnotationConfigApplicationContext(VideoSystemConfig03.class);
		
		DVDPlayer dvdPlayer = appCtx.getBean(DVDPlayer.class);
		dvdPlayer.play();
		
		((ConfigurableApplicationContext) appCtx).close();
	}
}
