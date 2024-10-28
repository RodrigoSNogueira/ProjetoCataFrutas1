package painel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    // Variáveis para verificar se as teclas estão pressionadas
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    
    // Este método é chamado quando uma tecla é pressionada e solta, mas não usado aqui
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub (Para implementar caso necessário)
    }

    // Este método é chamado quando uma tecla é pressionada
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // Obtém o código da tecla pressionada
        
        // Verifica se as teclas W, A, S, D foram pressionadas e altera os valores booleanos
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
    }

    // Este método é chamado quando uma tecla é solta
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode(); // Obtém o código da tecla solta
        
        // Verifica se as teclas W, A, S, D foram soltas e altera os valores booleanos
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
