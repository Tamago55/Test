import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class KatakanaTranslator {

    private HashMap<String, String> japaneseToMorseLib = new HashMap<>(), morseTojapaneseLib = new HashMap<>();
    {
        japaneseToMorseLib.put("ア", "--.--");
        japaneseToMorseLib.put("イ", ".-");
        japaneseToMorseLib.put("ウ", "..-");
        japaneseToMorseLib.put("エ", "-.---");
        japaneseToMorseLib.put("オ", ".-...");

        japaneseToMorseLib.put("カ", ".-..");
        japaneseToMorseLib.put("キ", "-.-..");
        japaneseToMorseLib.put("ク", "...-");
        japaneseToMorseLib.put("ケ", "-.--");
        japaneseToMorseLib.put("コ", "----");

        japaneseToMorseLib.put("サ", "-.-.-");
        japaneseToMorseLib.put("シ", "--.-.");
        japaneseToMorseLib.put("ス", "---.-");
        japaneseToMorseLib.put("セ", ".---.");
        japaneseToMorseLib.put("ソ", "---.");

        japaneseToMorseLib.put("タ", "-.");
        japaneseToMorseLib.put("チ", "..-.");
        japaneseToMorseLib.put("ツ", ".--.");
        japaneseToMorseLib.put("テ", ".-.--");
        japaneseToMorseLib.put("ト", "..--");

        japaneseToMorseLib.put("ナ", ".-.");
        japaneseToMorseLib.put("ニ", "-.-.");
        japaneseToMorseLib.put("ヌ", "....");
        japaneseToMorseLib.put("ネ", "--.-");
        japaneseToMorseLib.put("ノ", "..--.");

        japaneseToMorseLib.put("ハ", "-...");
        japaneseToMorseLib.put("ヒ", "--..-");
        japaneseToMorseLib.put("フ", "--..");
        japaneseToMorseLib.put("ヘ", ".");
        japaneseToMorseLib.put("ホ", "-..");

        japaneseToMorseLib.put("マ", "-..-");
        japaneseToMorseLib.put("ミ", "..-..");
        japaneseToMorseLib.put("ム", "-");
        japaneseToMorseLib.put("メ", "-...-");
        japaneseToMorseLib.put("モ", "-..-.");

        japaneseToMorseLib.put("ヤ", ".--");
        japaneseToMorseLib.put("ユ", "-..--");
        japaneseToMorseLib.put("ヨ", "--");

        japaneseToMorseLib.put("ラ", "..."); 
        japaneseToMorseLib.put("リ", "--.");
        japaneseToMorseLib.put("ル", "-.--.");
        japaneseToMorseLib.put("レ", "---");
        japaneseToMorseLib.put("ロ", ".-.-");

        japaneseToMorseLib.put("ワ", "-.-");
        japaneseToMorseLib.put("ヲ", ".---");
        japaneseToMorseLib.put("ン", ".-.-.");

        japaneseToMorseLib.put("゛", "..");
        japaneseToMorseLib.put("゜", "..--");
        japaneseToMorseLib.put("。", ".----.");
        japaneseToMorseLib.put("、", ".-.-");
        japaneseToMorseLib.put("　", " ");

        //fill the morseTojapaneseLib
        List<Object> values = Arrays.asList(japaneseToMorseLib.values().toArray());
        List<Object> keys = Arrays.asList(japaneseToMorseLib.keySet().toArray());
        for(int i = 0; i < values.size(); i++) {
            morseTojapaneseLib.put(values.get(i).toString(), keys.get(i).toString());
        }

    }

    private String info = "Morse Translator from Japanese Katakana\n" +
                        "Note: \n" +
                        "Input Dakuten(゛) and Handakuten(゜) separately(e.g. デ -> 「テ」+「゛」)\n" +
                        "Use [。] or [、] to separate sentences.\n" + 
                        "(No line breaks and space)\n" ;

    private JTextArea morseTextArea;

    // Constructor            
    public KatakanaTranslator() {
        //Katakana area
        JTextArea japaneseTextArea = new JTextArea(20,20);
        japaneseTextArea.setText("カタカナテ゛カイテ");
        japaneseTextArea.setLineWrap(true);
        japaneseTextArea.setWrapStyleWord(true);
        japaneseTextArea.setMargin(new Insets(5, 5, 5,5));
        JLabel japaneseTextLabel = new JLabel("japanese Text");
        japaneseTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton clearjapaneseText = new JButton("Clear Japanese Text");
        JButton japaneseToMorseBt = new JButton("japanese >> Morse");

        JPanel japaneseControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        japaneseControlPanel.add(clearjapaneseText);
        japaneseControlPanel.add(japaneseToMorseBt);


        JPanel japaneseTextPanel = new JPanel();
        japaneseTextPanel.setLayout(new BorderLayout());
        japaneseTextPanel.add(japaneseTextLabel, BorderLayout.NORTH);
        japaneseTextPanel.add(new JScrollPane(japaneseTextArea), BorderLayout.CENTER);
        japaneseTextPanel.add(japaneseControlPanel, BorderLayout.SOUTH);

        //Morse area
        morseTextArea = new JTextArea();
        morseTextArea.setText(".-");
        morseTextArea.setLineWrap(true);
        morseTextArea.setWrapStyleWord(true);
        morseTextArea.setMargin(new Insets(5, 5, 5,5));
        morseTextArea.setFont(new Font("", 0, 20));

        JLabel morseTextLabel = new JLabel("Morse Code");
        morseTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton morseTojapaneseBt = new JButton("Morse >> japanese");
        JButton clearMorseText = new JButton("Clear Morse Text");

        JPanel morseControlPanel = new JPanel();
        morseControlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        morseControlPanel.add(morseTojapaneseBt);
        morseControlPanel.add(clearMorseText);

        JPanel morseTextPanel = new JPanel();
        morseTextPanel.setLayout(new BorderLayout());
        morseTextPanel.add(morseTextLabel, BorderLayout.NORTH);
        morseTextPanel.add(new JScrollPane(morseTextArea), BorderLayout.CENTER);
        morseTextPanel.add(morseControlPanel, BorderLayout.SOUTH);

        //Light area        
        StopLightPanel stopLightPanel = new StopLightPanel(morseTextArea);
        
        JSplitPane splitPane =
                new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, japaneseTextPanel, morseTextPanel);
        splitPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JTextArea infoTextArea = new JTextArea();
        infoTextArea.setLineWrap(true);
        infoTextArea.setWrapStyleWord(true);
        infoTextArea.setText(info);
        infoTextArea.setBackground(new Color(241,241,241));
        infoTextArea.setEditable(false);
        infoTextArea.setMargin(new Insets(5, 5, 5,5));


        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(infoTextArea, BorderLayout.CENTER);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        mainPanel.add(stopLightPanel, BorderLayout.SOUTH);

        japaneseToMorseBt.addActionListener((e) -> {
            String japanese = japaneseTextArea.getText().trim();
            morseTextArea.setText(japaneseWordToMorseWord(japanese));
        });

        morseTojapaneseBt.addActionListener((e) -> {
            String morse = morseTextArea.getText().trim();
            japaneseTextArea.setText(morseWordTojapaneseWord(morse));
            // stopLightPanel.light.startDisplayingMorse(morseTextArea.getText());
        });

        japaneseTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                //when space bar is pressed (or back space) do the conversion
                if(Character.isWhitespace(e.getKeyChar()) || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    morseTextArea.setText(japaneseWordToMorseWord(japaneseTextArea.getText()));
                }
            }
        });

        morseTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                //when space bar is pressed (or back space) do the conversion
                if(Character.isWhitespace(e.getKeyChar()) || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    japaneseTextArea.setText(morseWordTojapaneseWord(morseTextArea.getText()));
                }
            }
        });

        clearjapaneseText.addActionListener((e) -> {
            japaneseTextArea.setText(null);
        });

        clearMorseText.addActionListener((e) -> {
            morseTextArea.setText(null);
        });

        JFrame frame = new JFrame();
        frame.setTitle("Morse Translator from Japanese");
        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setSize(new Dimension(800, 650));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        splitPane.setDividerLocation(frame.getWidth() / 2);
        japaneseToMorseBt.doClick();

    }

    public String japaneseWordToMorseWord(String japaneseWord) {
        StringBuffer buffer = new StringBuffer();
        Stream.of(japaneseWord.split("[ \n]"))
                .forEach( s -> {
                    for(char c: s.toCharArray()) {
                        buffer.append(japaneseToMorseLib.containsKey(String.valueOf(c).toUpperCase()) ? japaneseToMorseLib.get(String.valueOf(c).toUpperCase()) + " " : "?? ");
                    }
                    buffer.append("/");
                });
        return buffer.toString();
    }


    public String morseWordTojapaneseWord(String morseWord) {
        StringBuffer buffer = new StringBuffer();
        Stream.of(morseWord.split("[\\s\\n]"))
                .filter((s) -> s != null && !s.isEmpty())
                .forEach( s -> {
                        if(s.equalsIgnoreCase("/") || s.equalsIgnoreCase("|")) {
                            buffer.append(" ");
                        } else {;
                            buffer.append((morseTojapaneseLib.containsKey(s) ? morseTojapaneseLib.get(s) : "?? ").toLowerCase());
                        }
                });
        return buffer.toString();
    }

    public class StopLightPanel extends JPanel {
        StopLightDrawing light;
    
        public StopLightPanel(JTextArea morseTextArea) {
            light = new StopLightDrawing();
            JButton switchButton = new JButton("Start");
            switchButton.addActionListener(e -> {
                // Switch ボタンが押されたときの処理
                String morseText = morseTextArea.getText().trim();
                light.startDisplayingMorse(morseText); // モールス信号の表示をリセットして開始
            });
    
            JButton resetButton = new JButton("Reset");
            resetButton.addActionListener(e -> {
                // Reset ボタンが押されたときの処理
                light.resetDisplay(); // モールス信号の表示をリセットして停止
            });
    
            this.add(light);
            this.add(switchButton);
            this.add(resetButton); // Reset ボタンをパネルに追加
        }
    }

        public class StopLightDrawing extends JComponent {
            private boolean isRed = false;
            private javax.swing.Timer timer;
            private String morseCode;
            private int index = 0;
        
            public StopLightDrawing() {
                this.setPreferredSize(new Dimension(160, 90));
            }
        
            // public void startDisplayingMorse(String morse) {
            //     resetDisplay();
            //     morseCode = morse;
            //     index = 0;
            //     timer = new javax.swing.Timer(1000, e -> displayNextMorseSymbol());
            //     timer.start();
            // }

            public void startDisplayingMorse(String morse) {
                resetDisplay();
                
                // モールス信号の各シンボルの間に 'b' を挿入（ただし、前の文字が . または - の場合のみ）
                StringBuilder modifiedMorse = new StringBuilder();
                for (int i = 0; i < morse.length(); i++) {
                    char currentChar = morse.charAt(i);
                    modifiedMorse.append(currentChar); // 元のシンボルを追加
            
                    // シンボルが . または - で、かつ最後の文字ではない場合に 'b' を追加
                    if ((currentChar == '.' || currentChar == '-') && i < morse.length() - 1) {
                        modifiedMorse.append('b');
                    }
                }
            
                morseCode = modifiedMorse.toString();
                index = 0;
                timer = new javax.swing.Timer(1000, e -> displayNextMorseSymbol());
                timer.start();
            }
        
        private void displayNextMorseSymbol() {
            if (index < morseCode.length()) {
                char symbol = morseCode.charAt(index);
            
            // System.out.println(morseCode);

            switch (symbol) {
                case '.':
                    isRed = true; // 点「.」は赤色
                    System.out.println("[.]");
                    repaint(); // GUIを更新
                    timer.setInitialDelay(500); // 点のための0.5秒遅延
                    timer.restart(); // タイマーを再起動
                    break;
                case '-':
                    isRed = true; // 線「-」は赤色
                    System.out.println("[-]");
                    repaint(); // GUIを更新
                    timer.setInitialDelay(1500); // 線のための3秒遅延
                    timer.restart(); // タイマーを再起動
                    break;
                case ' ':
                    isRed = false; // 文字間の空白はライトを消す
                    System.out.println("[ ]");
                    repaint(); // GUIを更新
                    timer.setInitialDelay(1500); // 文字間の空白のための3秒遅延
                    timer.restart(); // タイマーを再起動
                    break;
                case '/':
                    isRed = false; // 単語間の空白はライトを消す
                    System.out.println("finish a word");
                    repaint(); // GUIを更新
                    timer.setInitialDelay(3000); // 単語間の空白のための7秒遅延
                    timer.restart(); // タイマーを再起動
                    break;
                case 'b':
                    isRed = false; // 単語間の空白はライトを消す
                    System.out.println("break");
                    repaint(); // GUIを更新
                    timer.setInitialDelay(500); // 単語間の空白のための7秒遅延
                    timer.restart(); // タイマーを再起動
                    break;
                default:
                    // 予期しないシンボルがある場合は何もしない
                    break;
            }
                index++; // 次のシンボルへ移動
            } else {
                resetDisplay(); // モールス信号の表示が終了したらリセット
            }
        }

        public void resetDisplay() {
            if (timer != null) {
                timer.stop();
            }
            isRed = false; 
            repaint();
        }    

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.black);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(isRed ? Color.red : Color.gray);
            int radius = 30;
            int x = (getWidth() - radius * 2) / 2;
            int y = (getHeight() - radius * 2) / 2;
            g.fillOval(x, y, radius * 2, radius * 2);
        }

        public void changeColor() {

            isRed = !isRed;
            repaint();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new KatakanaTranslator();
        });
    }

}