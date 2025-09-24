package com.noinc.bloomsjeopardy;

import com.noinc.bloomsjeopardy.controller.GameEngine;
import org.junit.Test;
import org.mockito.MockedConstruction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;

public class AppTest {

    @Test
    public void main(){
        try (MockedConstruction<GameEngine> mocked = mockConstruction(GameEngine.class)) {
            assertDoesNotThrow(() -> App.main(new String[]{}));
            verify(mocked.constructed().getFirst()).addActionListeners();
        }
    }
}