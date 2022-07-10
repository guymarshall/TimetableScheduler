package com.gmail.guymarshall9.timetablescheduler;

public class WorkerResult
{
	private boolean m_bProcessingCompleted = false;
	private Throwable m_thThrowable = null;
	private int m_nRun = 0;

	public WorkerResult(boolean bProcessingCompleted, Throwable thThrowable, int nRun)
	{
		m_bProcessingCompleted = bProcessingCompleted;
		m_thThrowable = thThrowable;
		m_nRun = nRun;
	}

	public boolean getProcessingCompleted()
	{
		return m_bProcessingCompleted;
	}

	public Throwable getThrowable()
	{
		return m_thThrowable;
	}

	public int getRun()
	{
		return m_nRun;
	}
}
