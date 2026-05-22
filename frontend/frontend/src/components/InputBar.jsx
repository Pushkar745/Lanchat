import React, { useState, useRef } from 'react';

export default function InputBar({ onSend, onTyping, disabled }) {
  const [value, setValue] = useState('');
  const inputRef          = useRef(null);

  function handleSend() {
    const trimmed = value.trim();
    if (!trimmed || disabled) return;
    onSend(trimmed);
    setValue('');
    inputRef.current?.focus();
  }

  function handleKeyDown(e) {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      handleSend();
      return;
    }
    // Fire typing event on every keystroke except Enter
    onTyping?.();
  }

  const charCount    = value.length;
  const nearLimit    = charCount > 800;
  const atLimit      = charCount >= 1000;
  const counterColor = atLimit
    ? 'var(--red)'
    : nearLimit ? '#fbbf24' : 'var(--hint)';

  return (
    <div className="input-bar">
      <div style={{ flex: 1, position: 'relative' }}>
        <input
          ref={inputRef}
          className="input-field"
          type="text"
          placeholder={disabled ? 'Reconnecting…' : 'Message the team…'}
          value={value}
          maxLength={1000}
          disabled={disabled}
          onChange={e => setValue(e.target.value)}
          onKeyDown={handleKeyDown}
          autoComplete="off"
          spellCheck={true}
        />
        {charCount > 700 && (
          <span style={{
            position:      'absolute',
            right:         '10px',
            top:           '50%',
            transform:     'translateY(-50%)',
            fontSize:      '10px',
            fontFamily:    'var(--mono)',
            color:         counterColor,
            pointerEvents: 'none',
          }}>
            {charCount}/1000
          </span>
        )}
      </div>

      <button
        className="send-button"
        onClick={handleSend}
        disabled={disabled || !value.trim()}
        title="Send (Enter)"
      >
        <svg viewBox="0 0 24 24">
          <path d="M2.01 21L23 12 2.01 3 2 10l15 2-15 2z" />
        </svg>
      </button>
    </div>
  );
}