package edu.postech.csed332.team3.markdowndoc;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ActionCallback;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowContentUiType;
import com.intellij.openapi.wm.ToolWindowType;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.content.ContentManagerListener;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.SystemIndependent;
import org.junit.Test;
import org.picocontainer.PicoContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JxBrowserTest {
    @Test
    public void smokeTest() {
        assertEquals(3, 1 + 2);
    }

    @Test
    public void windowFactoryTest() {
        final JxBrowserWindowFactory jxBrowserWindowFactory = new JxBrowserWindowFactory();
        assertThrows(IllegalArgumentException.class, () -> jxBrowserWindowFactory.createToolWindowContent(null, null));
        assertDoesNotThrow(()-> jxBrowserWindowFactory.createToolWindowContent(new Project() {
            @Override
            public @NotNull String getName() {
                return null;
            }

            @Override
            public VirtualFile getBaseDir() {
                return null;
            }

            @Override
            public @Nullable @SystemIndependent String getBasePath() {
                return null;
            }

            @Override
            public @Nullable VirtualFile getProjectFile() {
                return null;
            }

            @Override
            public @Nullable @SystemIndependent String getProjectFilePath() {
                return null;
            }

            @Override
            public @Nullable VirtualFile getWorkspaceFile() {
                return null;
            }

            @Override
            public @NotNull String getLocationHash() {
                return null;
            }

            @Override
            public void save() {

            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public boolean isInitialized() {
                return false;
            }

            @Override
            public <T> T getComponent(@NotNull Class<T> interfaceClass) {
                return null;
            }

            @Override
            public @NotNull PicoContainer getPicoContainer() {
                return null;
            }

            @Override
            public @NotNull MessageBus getMessageBus() {
                return null;
            }

            @Override
            public boolean isDisposed() {
                return false;
            }

            @Override
            public @NotNull Condition<?> getDisposed() {
                return null;
            }

            @Override
            public void dispose() {

            }

            @Override
            public <T> @Nullable T getUserData(@NotNull Key<T> key) {
                return null;
            }

            @Override
            public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {

            }
        }, new ToolWindow() {
            @Override
            public @NotNull String getId() {
                return null;
            }

            @Override
            public boolean isActive() {
                return false;
            }

            @Override
            public void activate(@Nullable Runnable runnable) {

            }

            @Override
            public void activate(@Nullable Runnable runnable, boolean autoFocusContents) {

            }

            @Override
            public void activate(@Nullable Runnable runnable, boolean autoFocusContents, boolean forced) {

            }

            @Override
            public boolean isVisible() {
                return false;
            }

            @Override
            public void show(@Nullable Runnable runnable) {

            }

            @Override
            public void hide(@Nullable Runnable runnable) {

            }

            @Override
            public @NotNull ToolWindowAnchor getAnchor() {
                return null;
            }

            @Override
            public void setAnchor(@NotNull ToolWindowAnchor anchor, @Nullable Runnable runnable) {

            }

            @Override
            public boolean isSplitMode() {
                return false;
            }

            @Override
            public void setSplitMode(boolean split, @Nullable Runnable runnable) {

            }

            @Override
            public boolean isAutoHide() {
                return false;
            }

            @Override
            public void setAutoHide(boolean value) {

            }

            @Override
            public @NotNull ToolWindowType getType() {
                return null;
            }

            @Override
            public void setType(@NotNull ToolWindowType type, @Nullable Runnable runnable) {

            }

            @Override
            public @Nullable Icon getIcon() {
                return null;
            }

            @Override
            public void setIcon(@NotNull Icon icon) {

            }

            @Override
            public @Nullable String getTitle() {
                return null;
            }

            @Override
            public void setTitle(String title) {

            }

            @Override
            public @NotNull String getStripeTitle() {
                return null;
            }

            @Override
            public void setStripeTitle(@NotNull String title) {

            }

            @Override
            public boolean isAvailable() {
                return false;
            }

            @Override
            public void setAvailable(boolean value) {

            }

            @Override
            public void setAvailable(boolean value, @Nullable Runnable runnable) {

            }

            @Override
            public void setContentUiType(@NotNull ToolWindowContentUiType type, @Nullable Runnable runnable) {

            }

            @Override
            public void setDefaultContentUiType(@NotNull ToolWindowContentUiType type) {

            }

            @Override
            public @NotNull ToolWindowContentUiType getContentUiType() {
                return null;
            }

            @Override
            public void installWatcher(ContentManager contentManager) {

            }

            @Override
            public @NotNull JComponent getComponent() {
                return null;
            }

            @Override
            public @NotNull ContentManager getContentManager() {
                return null;
            }

            @Override
            public @Nullable ContentManager getContentManagerIfCreated() {
                return null;
            }

            @Override
            public void addContentManagerListener(@NotNull ContentManagerListener listener) {

            }

            @Override
            public void setDefaultState(@Nullable ToolWindowAnchor anchor, @Nullable ToolWindowType type, @Nullable Rectangle floatingBounds) {

            }

            @Override
            public void setToHideOnEmptyContent(boolean hideOnEmpty) {

            }

            @Override
            public boolean isShowStripeButton() {
                return false;
            }

            @Override
            public void setShowStripeButton(boolean value) {

            }

            @Override
            public boolean isDisposed() {
                return false;
            }

            @Override
            public void showContentPopup(@NotNull InputEvent inputEvent) {

            }

            @Override
            public @NotNull Disposable getDisposable() {
                return null;
            }

            @Override
            public void remove() {

            }

            @Override
            public void setTitleActions(@NotNull List<AnAction> actions) {

            }

            @Override
            public @NotNull ActionCallback getReady(@NotNull Object requestor) {
                return null;
            }
        }));
    }
}
