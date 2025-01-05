package com.hepl.SourceProject;

import java.util.ArrayList;
import java.util.List;

public class Game 
{
    private boolean[][] Map;
    private int[][] ValueMap;
    private int[] StartPos;
    private int[] PlayerPos;
    private int[] EndPos;
    private String moveMemo;

    public Game(boolean[][] map, int[] start, int[]end)
    {
        //Enregistre la carte
        Map = map.clone();
        //Enregistre la position inisiale
        StartPos = start.clone();
        PlayerPos = start.clone();
        //Enregistre la postion final
        EndPos = end.clone();

        //Calcule la distance réel entre chaque point et la position final
        ValueMap = new int[Map.length][Map[0].length];
        ValueMap[EndPos[0]][EndPos[1]] = 1;
        
        //List des tuiles déjà calculé au bord de la forontière du calcule
        List<int[]> boarder = new ArrayList<int[]>();
        boarder.add(EndPos);
        
        // Conteur de distance
        int count = 1;

        // Définition d'adjacent
        int[][] offsets = {{1,0},{-1,0},{0,1},{0,-1}};
        while(true)
        {
            count += 1;
            List<int[]> TilesToProcess = new ArrayList<int[]>();
            for (int[] tile : boarder)
            {
                for (int[] offset : offsets) 
                {
                    if(tile[0]+ offset[0] < Map.length && tile[0]+ offset[0] > -1)
                    {
                        if(tile[1] + offset[1] < Map[tile[0]+ offset[0]].length && tile[1] + offset[1] > -1)
                        {

                            if(Map[tile[0]+ offset[0]][tile[1] + offset[1]])
                            {
                                if(ValueMap[tile[0]+ offset[0]][tile[1] + offset[1]] == 0)
                                {
                                    int[] n = {tile[0]+ offset[0], tile[1] + offset[1]};
                                    TilesToProcess.add(n);
                                }
                            }
                        }
                        
                    }
                }
            }
            boarder = new ArrayList<int[]>();
            if(TilesToProcess.isEmpty())
            {
                break; 
            }
            for (int[] tile : TilesToProcess) 
            {
                ValueMap[tile[0]][tile[1]] = count; 
                boarder.add(tile);  
            }
        }
    }

    public void display(boolean v)
    {
        System.out.println("Basic Map");
        for (int y = 0; y < Map[0].length; y++) 
        {
            for (int x = 0; x < Map.length; x++) 
            {
                if(PlayerPos[0] == x && PlayerPos[1]== y)
                {
                    System.out.print("@");
                    continue;
                }
                if(Map[x][y])
                {
                    System.out.print("_");
                }else
                {
                    System.out.print("X");
                } 
            }  
            System.out.println("");  
        }
        System.out.println("");
        if(v){
        System.out.println(" Value Map");
        for (int y = 0; y < Map[0].length; y++) 
        {
            for (int x = 0; x < Map.length; x++) 
            {
                System.out.print(ValueMap[x][y]);
            }  
            System.out.println("");  
        }
    }

    }
    
    public int PlayStatic(byte[] cmd)
    {
        for (int i = 0; i < cmd.length/3; i++) 
        {
            byte[] b = {cmd[3*i],cmd[(3*i)+1],cmd[(3*i)+2]};
            Move(b);
        }
        int ret = ValueMap[PlayerPos[0]][PlayerPos[1]];
        display(false);
        Reset();
        return ret;
    }
    private boolean GetMap(int x, int y)
    {
        if(x < 0 || x >= Map.length || y < 0 || y >= Map[0].length)
        {
            return false;
        }
        else
        {
            return Map[x][y];
        }
    }
    public void Move(byte[] cmd)
    {
        String sCmd = ByteToString(cmd[0])+ ByteToString(cmd[1])+ ByteToString(cmd[2]);
        if( PlayerPos[1]+1 == Map.length || !GetMap(PlayerPos[0], PlayerPos[1]+1))
        {
            moveMemo = sCmd;
            try{
            switch (sCmd) {
                case "000":
                    if(GetMap(PlayerPos[0],PlayerPos[1]-1) && GetMap(PlayerPos[0]-1,PlayerPos[1]-1))
                    {
                        PlayerPos[0] -= 1;
                        PlayerPos[1] -= 1;
                    }
                    break;
                case "001":
                    if(GetMap(PlayerPos[0]-1,PlayerPos[1]))
                    {
                        PlayerPos[0] -= 1;
                    }
                    break;
                case "010":
                    if(GetMap(PlayerPos[0]-1,PlayerPos[1]+1) && GetMap(PlayerPos[0]-1,PlayerPos[1]))
                        {
                            PlayerPos[0] -= 1;
                            PlayerPos[1] += 1;
                        }
                    
                    break;

                case "100":
                    if(GetMap(PlayerPos[0],PlayerPos[1]-1) && GetMap(PlayerPos[0]+1,PlayerPos[1]-1))
                    {
                        PlayerPos[0] += 1;
                        PlayerPos[1] -= 1;
                    }
                    break;
                case "101":
                    if(GetMap(PlayerPos[0]+1,PlayerPos[1]))
                    {
                        PlayerPos[0] += 1;
                    }
                    break;
                case "110":
                    if(GetMap(PlayerPos[0]+1,PlayerPos[1]+1) && GetMap(PlayerPos[0]-1,PlayerPos[1]))
                        {
                            PlayerPos[0] += 1;
                            PlayerPos[1] += 1;
                        }
                    
                    break;
            
                default:
                    break;
            }
            }catch(IndexOutOfBoundsException e)
            {
            
            }
        }else
        {
          PlayerPos[1] += 1;
          if(moveMemo.charAt(0) == '0')
          {
            if(GetMap(PlayerPos[0]-1,PlayerPos[1]))
            {
                PlayerPos[0] -= 1;
            } 
          }else
          {
            if(GetMap(PlayerPos[0]+1,PlayerPos[1]))
                    {
                        PlayerPos[0] += 1;
                    }
          }
        }
    }
    private String ByteToString(byte b)
    {
        if(b == 1)
        {
            return "1";
        }else
        {
            return "0";
        }
    }
    public void Reset()
    {
        PlayerPos = StartPos.clone();
    }
    public float[] getSurounding()
    {
        float[] ret = new float[9];
        ret[0] = GetMap(PlayerPos[0]-1, PlayerPos[1]-1)? 0 : 1;
        ret[1] = GetMap(PlayerPos[0], PlayerPos[1]-1)? 0 : 1;
        ret[2] = GetMap(PlayerPos[0]+1, PlayerPos[1]-1)? 0 : 1;

        ret[3] = GetMap(PlayerPos[0]-1, PlayerPos[1])? 0 : 1;
        ret[4] = GetMap(PlayerPos[0], PlayerPos[1])? 0 : 1;
        ret[5] = GetMap(PlayerPos[0]+1, PlayerPos[1])? 0 : 1;

        ret[6] = GetMap(PlayerPos[0]-1, PlayerPos[1]+1)? 0 : 1;
        ret[7] = GetMap(PlayerPos[0], PlayerPos[1]+1)? 0 : 1;
        ret[8] = GetMap(PlayerPos[0]+1, PlayerPos[1]+1)? 0 : 1;
        return ret;
    }
    public void Move(float[] cmd)
    {
        byte[] b = new byte[3];
        for (int i = 0; i < 3; i++)
        {
            b[i]= cmd[i] < 0.5 ? (byte)0 : (byte)1;
        }
        Move(b);  
    }
    public int getPlayerValue()
    {
        return ValueMap[PlayerPos[0]][PlayerPos[1]];
    }
}
