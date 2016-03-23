package com.example.jiyong.vccreate;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Size;

import java.lang.reflect.Array;
import java.util.BitSet;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void Form1_Load(Object sender, eventArgs e) ///////////////// "Click Event 부분 공부"
    {

    }

    private Size ImageSize = new Size(437,106);
    private static int GenerateImageCount = 2;
    private Bitmap[] img;

    private Canvas GenerateImage (String text) //return Canvas[]?
    {
        Bitmap finalImage = Bitmap.createBitmap(ImageSize.getWidth(), ImageSize.getHeight(), Bitmap.Config.ARGB_8888); //최종이미지
        Bitmap tempImage = Bitmap.createBitmap(ImageSize.getWidth() / 2, ImageSize.getHeight(), Bitmap.Config.ARGB_8888); //임시이미지
        Bitmap[] image = new Bitmap[GenerateImageCount]; //Share 1, Share 2 저장
        /////Seed는 ID와 PW의 조합으로 넣기
        Random rand = new Random(); //난수

        Canvas finalCancas = new Canvas(finalImage); // Bitmap을 만들고 나면 Canvas를 생성해야 한다. =====> 이거 쓰나??

        SolidBrush brush=new SolidBrush(Color.Black); //그리는 Brush
        Point mid = new Point(ImageSize.getWidth()/2,ImageSize.getHeight()/2); // 정중앙
        Canvas g = Canvas.drawPicture(finalImage); // finalImage 그리기
        Canvas gtemp = Canvas.FromImage(tempImage); // tempImage 그리기

        // 문자를 상하좌우의 중심에 ==> XML
        // 문자 입력 받은건가 ?-?
        StringFormat sf = new StringFormat();
        sf.Alignment = StringAlignment.Center;
        sf.LineAlignment = StringAlignment.Center;

        //Font font = new Font("Times New Roman",48); //폰트와 글자크기 지정==>Paint에서 해주기!!!
        int fontColor;

        g.drawText(text,10,10,Paint) ///시작 x,y 와 paint 정의하기 !!!!
                //(text, font, brush, mid, sf); //문자, 폰트, 검정색 브러쉬, 중앙, 정렬을 입력받아 문자열을 그림
        gtemp.drawBitmap(finalImage, 0, 0, Paint); //temp? final? 이미지를 그림?
/*
        //Share 1, Share 2 를 사이즈에 맞게 생성 (빈 Bitmap)
        for(int i = 0; i < image.length; i++) //image의 길이 (2개)
        {
            image[i] = Bitmap.createBitmap(ImageSize.getWidth(), ImageSize.getHeight(),Bitmap.Config.ARGB_8888);
            Canvas imagec[i] = new Canvas [image[i]];
        }
*/
        // KeyShare,MasterShare를 그릴 Bitmap과 Canvas 선언
        image[0] = Bitmap.createBitmap(ImageSize.getWidth(), ImageSize.getHeight(),Bitmap.Config.ARGB_8888);
        image[1] = Bitmap.createBitmap(ImageSize.getWidth(), ImageSize.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas keyShare = new Canvas(image[0]);
        Canvas MasterShare = new Canvas(image[1]);

        int index = -1; //index의 역할 --> ?????
        int width = tempImage.getWidth(); //입력받은 image의 width
        int height = tempImage.getHeight(); //입력받은 image의 height

        for(int x = 0; x < width; x +=1)
        {
            for(int y = 0; y < height ; y+=1) // (0,0) (0,1) (0,2) (0,3) (0,4) .... (0,height) / (1,0) (1,1) (1,2) ... (1,height) /// (width,height)
            {
                fontColor = tempImage.getPixel(x,y); // tempImage의 Pixel을 가져와 x, y에 저장함. Black인지 Empty인지?!
                index=rand.nextInt(5); // ?????????????????????????????????????// share의 length 만큼 (2만큼?) //// 랜덤하게 Create 하는부분 이해하기 !!!!!!!!!!
                if(isEmpty(fontColor)) // 그림 pixel의 fontcolor가 empty이면 --> 그대로 유지! // XML에 id로 저장
                {
                    for(int i = 0; i < image.length; i++)
                    {
                        if(index == 0)
                        {
                            image[i].setPixel(x * 2, y, getColor(R.color.black));
                            image[i].setPixel(x * 2 + 1, y, empty);
                        }
                        else
                        {
                            image[i].setPixel(x * 2, y, getColor(empty));
                            image[i].setPixel(x * 2 + 1, y, getColor(R.color.black));
                        }
                    }
                }
                else
                {
                    for(int i = 0; i < image.length; i++)
                    {
                        if((index + i) % image.length == 0)
                        {
                            image[i].setPixel(x * 2, y, getColor(R.color.black));
                            image[i].setPixel(x * 2 + 1, y, getColor(empty));
                        }
                        else
                        {
                            image[i].setPixel(x * 2, y, getColor(empty));
                            image[i].setPixel(x * 2 + 1, y, getColor(R.color.black));
                        }
                    }
                }
            }
        }
        return keyShare;
    }

    private void button1_Click(object sender, EventArgs e)
    {
        if(textBox1.Text !="")
        {
            if(img!=null) {
                for (int i = img.length - 1; i > 0; i--) {
                    img[i].Dispose();
                }
                Array.Clear(ima, 0, img.length);
            }
            img=GenerateImage(textBox1.Text);
            panel1.Invalidata();

        }
    }

    private void panel1_Paint(object sender, PaintEventArgs e)
    {
        if(img!=null)
        {
            Graphics g = e.Graphics;
            Rectangle rect = new Rectangle(0,0,0,0);
            for(int i = 0; i < img.length; i++)
            {
                rect.Size = img[i].Size;
                g.DrawImage(img[i], rect);
                rect.Y+=img[i].getHeight()+5;
            }
            g.DrawLine(new Pen(new SolidBrush(Color.BLACK), 1), rect.location,new Point(rect.Y));
            rect.Y += 5;
            for(int i = 0; i < img.length ; i++)
            {
                rect.Size = img[i].Size;
                g.DrawImage(img[i].rect);
            }
        }

        private void saveImageToolStripMunuItem_Click(object sender, EventArgs e)
    {
        if(img==null)
        {
            MessageBox=Show("X");
            return;
        }
        if(sfd.ShowDialog()==DialogResult.OK)
        {
            string fName= Path.GetFileNameWithoutExtension(sfd.FileName);
            string fPath = sfd.FileName;
            String sPath = fName;
            for(int i = 0; i < img.length; i++)
            {
                sPath = fPath.Replace(fName,fName+1);
                img[i].save(sPath, ImageFormat.png);
            }
        }
    }
    }

}
